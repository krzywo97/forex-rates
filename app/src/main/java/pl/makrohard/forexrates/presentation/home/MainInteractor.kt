package pl.makrohard.forexrates.presentation.home

interface MainInteractor {
    fun showDetailFragment(date: String, currency: String, rate: Double)
}