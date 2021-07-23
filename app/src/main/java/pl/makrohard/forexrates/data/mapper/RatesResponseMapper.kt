package pl.makrohard.forexrates.data.mapper

import pl.makrohard.forexrates.data.dto.RatesResponse
import pl.makrohard.forexrates.domain.model.Rate

object RatesResponseMapper {
    fun mapToRatesList(response: RatesResponse): List<Rate> {
        return response.rates.map { rate ->
            Rate(rate.key, rate.value)
        }
    }
}