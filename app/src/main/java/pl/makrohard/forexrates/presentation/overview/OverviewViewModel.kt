package pl.makrohard.forexrates.presentation.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.makrohard.forexrates.domain.model.Rate
import pl.makrohard.forexrates.domain.usecase.GetRatesOverviewUseCase
import javax.inject.Inject

class OverviewViewModel : ViewModel() {
    @Inject
    private lateinit var getRatesOverviewUseCase: GetRatesOverviewUseCase

    private val isLoading = MutableLiveData(false)
    private val exchangeRates = MutableLiveData<List<Rate>>()

    fun loadData(year: Int, month: Int, day: Int, currencies: List<String>) {
        getRatesOverviewUseCase.invoke(year, month, day, currencies)
            .subscribe()
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun getExchangeRates(): LiveData<List<Rate>> {
        return exchangeRates
    }
}