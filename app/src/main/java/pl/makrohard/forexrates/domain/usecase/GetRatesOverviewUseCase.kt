package pl.makrohard.forexrates.domain.usecase

import io.reactivex.Observable
import pl.makrohard.forexrates.domain.model.Rate

interface GetRatesOverviewUseCase {
    fun invoke(
        year: String,
        month: String,
        day: String,
        currencies: List<String>
    ): Observable<List<Rate>>
}