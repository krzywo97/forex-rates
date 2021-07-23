package pl.makrohard.forexrates.data.repository

import io.reactivex.Single
import pl.makrohard.forexrates.data.dto.RatesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RatesRepositoryContract {
    @GET("/{year}-{month}-{day}")
    fun getRates(
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day: String,
        @Query("symbols") currencies: String
    ): Single<RatesResponse>
}