package pl.makrohard.forexrates.data.repository

import io.reactivex.Single
import pl.makrohard.forexrates.data.dto.RatesResponse
import retrofit2.http.GET

interface RatesRepositoryContract {
    @GET("{year}-{month}-{day}")
    fun getRates(year: Int, month: Int, day: Int, currencies: String): Single<RatesResponse>
}