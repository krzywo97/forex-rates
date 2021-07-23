package pl.makrohard.forexrates.domain.usecase

import io.reactivex.Observable
import pl.makrohard.forexrates.domain.model.Rate
import pl.makrohard.forexrates.domain.repository.RatesRepository
import javax.inject.Inject

class GetRatesOverviewUseCaseImpl @Inject constructor() : GetRatesOverviewUseCase {
    @Inject
    lateinit var ratesRepository: RatesRepository

    override fun invoke(
        year: String,
        month: String,
        day: String,
        currencies: List<String>
    ): Observable<List<Rate>> {
        return ratesRepository.getRates(year, month, day, currencies)
    }
}