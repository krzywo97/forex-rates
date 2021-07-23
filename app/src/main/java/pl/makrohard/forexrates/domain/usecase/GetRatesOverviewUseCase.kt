package pl.makrohard.forexrates.domain.usecase

import io.reactivex.Observable
import pl.makrohard.forexrates.domain.model.Rate

interface GetRatesOverviewUseCase {
    fun invoke(year: Int, month: Int, day: Int, currencies: List<String>): Observable<List<Rate>>
}