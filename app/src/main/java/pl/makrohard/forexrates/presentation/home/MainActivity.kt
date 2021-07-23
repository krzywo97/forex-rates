package pl.makrohard.forexrates.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pl.makrohard.forexrates.R
import pl.makrohard.forexrates.databinding.ActivityMainBinding
import pl.makrohard.forexrates.presentation.overview.OverviewFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OverviewFragment())
            .commit()
    }
}