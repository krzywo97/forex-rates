package pl.makrohard.forexrates.domain.model

data class DailyRates(
    val date: String,
    val rates: List<Rate>
)