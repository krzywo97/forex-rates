package pl.makrohard.forexrates.data.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.makrohard.forexrates.BuildConfig
import pl.makrohard.forexrates.data.mapper.RatesResponseMapper
import pl.makrohard.forexrates.domain.model.Rate
import pl.makrohard.forexrates.domain.repository.RatesRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Inject

class FixerRepository @Inject constructor() : RatesRepository {
    // This interceptor injects the api key to every request it intercepts
    private val requestInterceptor = Interceptor { chain ->
        val request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("access_key", BuildConfig.FIXER_API_KEY)
            .build()

        chain.proceed(request.newBuilder().url(url).build())
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(requestInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(client)
        .build()
        .create(RatesRepositoryContract::class.java)

    override fun getRates(
        year: String,
        month: String,
        day: String,
        currencies: List<String>
    ): Observable<List<Rate>> {
        val currenciesString = currencies.joinToString { cur ->
            cur.uppercase(Locale.getDefault())
        }

        return retrofit.getRates(year, month, day, currenciesString)
            .map(RatesResponseMapper::mapToRatesList)
            .toObservable()
    }
}