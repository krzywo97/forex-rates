package pl.makrohard.forexrates.domain.repository

import io.reactivex.Observable
import pl.makrohard.forexrates.domain.model.Rate

interface RatesRepository {
    fun getRates(year: Int, month: Int, day: Int, currencies: List<String>): Observable<List<Rate>>
}