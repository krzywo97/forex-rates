package pl.makrohard.forexrates.presentation.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.makrohard.forexrates.domain.model.DailyRates
import pl.makrohard.forexrates.domain.usecase.GetRatesOverviewUseCase
import pl.makrohard.forexrates.util.DateUtils.getDay
import pl.makrohard.forexrates.util.DateUtils.getMonth
import pl.makrohard.forexrates.util.DateUtils.getYear
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var getRatesOverviewUseCase: GetRatesOverviewUseCase

    private val isLoading = MutableLiveData(false)
    private val exchangeRates = MutableLiveData<List<DailyRates>>(emptyList())
    private val date = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    fun loadData(currencies: List<String>) {
        val year = date.getYear()
        val month = date.getMonth()
        val day = date.getDay()

        isLoading.value = true
        getRatesOverviewUseCase.invoke(year, month, day, currencies)
            .subscribe { rates ->
                viewModelScope.launch(Dispatchers.Main) {
                    val dateFormatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)
                    val formattedDate = dateFormatter.format(date.time)
                    val dailyRates = DailyRates(formattedDate, rates)

                    isLoading.value = false
                    exchangeRates.value = exchangeRates.value?.plus(dailyRates)
                }
            }

        date.add(Calendar.DATE, -1)
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun getExchangeRates(): LiveData<List<DailyRates>> {
        return exchangeRates
    }
}