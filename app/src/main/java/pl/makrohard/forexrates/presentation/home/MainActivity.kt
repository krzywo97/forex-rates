package pl.makrohard.forexrates.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pl.makrohard.forexrates.R
import pl.makrohard.forexrates.databinding.ActivityMainBinding
import pl.makrohard.forexrates.presentation.detail.DetailFragment
import pl.makrohard.forexrates.presentation.overview.OverviewFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainInteractor {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        showFragment(OverviewFragment(), false)
    }

    private fun showFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(fragment::class.qualifiedName)
        }

        transaction.commit()
    }

    override fun showDetailFragment(date: String, currency: String, rate: Double) {
        showFragment(DetailFragment.newInstance(date, currency, rate), true)
    }
}