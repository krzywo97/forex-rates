package pl.makrohard.forexrates.presentation.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.makrohard.forexrates.domain.model.Rate
import pl.makrohard.forexrates.domain.usecase.GetRatesOverviewUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var getRatesOverviewUseCase: GetRatesOverviewUseCase

    private val isLoading = MutableLiveData(false)
    private val exchangeRates = MutableLiveData<List<Rate>>(emptyList())
    private val date = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    fun loadData(currencies: List<String>) {
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString().padStart(2, '0')
        val day = date.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')

        isLoading.value = true
        getRatesOverviewUseCase.invoke(year, month, day, currencies)
            .subscribe { rates ->
                viewModelScope.launch(Dispatchers.Main) {
                    isLoading.value = false
                    exchangeRates.value = exchangeRates.value?.plus(rates)
                }
            }

        date.add(Calendar.DATE, -1)
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun getExchangeRates(): LiveData<List<Rate>> {
        return exchangeRates
    }
}