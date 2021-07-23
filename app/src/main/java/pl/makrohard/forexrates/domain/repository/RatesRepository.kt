package pl.makrohard.forexrates.domain.repository

import io.reactivex.Observable
import pl.makrohard.forexrates.domain.model.Rate

interface RatesRepository {
    fun getRates(
        year: String,
        month: String,
        day: String,
        currencies: List<String>
    ): Observable<List<Rate>>
}