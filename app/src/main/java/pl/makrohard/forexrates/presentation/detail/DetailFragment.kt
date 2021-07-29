package pl.makrohard.forexrates.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pl.makrohard.forexrates.databinding.FragmentDetailBinding

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var viewBinding: FragmentDetailBinding

    companion object {
        private const val DATE = "date"
        private const val CURRENCY = "currency"
        private const val RATE = "rate"

        fun newInstance(date: String, currency: String, rate: Double): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(DATE, date)
                    putString(CURRENCY, currency)
                    putDouble(RATE, rate)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val text =
            "1€ = " + arguments?.getDouble(RATE).toString() + " " + arguments?.getString(CURRENCY)

        viewBinding.date.text = arguments?.getString(DATE)
        viewBinding.rate.text = text
    }
}