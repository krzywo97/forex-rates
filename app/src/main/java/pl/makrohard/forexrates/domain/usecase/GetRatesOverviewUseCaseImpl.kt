package pl.makrohard.forexrates.domain.usecase

import io.reactivex.Observable
import pl.makrohard.forexrates.domain.model.Rate
import pl.makrohard.forexrates.domain.repository.RatesRepository
import javax.inject.Inject

class GetRatesOverviewUseCaseImpl : GetRatesOverviewUseCase {
    @Inject
    lateinit var ratesRepository: RatesRepository

    override fun invoke(
        year: Int,
        month: Int,
        day: Int,
        currencies: List<String>
    ): Observable<List<Rate>> {
        return ratesRepository.getRates(year, month, day, currencies)
    }
}