package pl.makrohard.forexrates.util

import java.util.*

object DateUtils {
    fun Calendar.getDay(): String {
        return get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
    }

    fun Calendar.getMonth(): String {
        return (get(Calendar.MONTH) + 1).toString().padStart(2, '0')
    }

    fun Calendar.getYear(): String {
        return get(Calendar.YEAR).toString()
    }
}