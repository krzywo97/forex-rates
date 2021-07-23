package pl.makrohard.forexrates.presentation.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import pl.makrohard.forexrates.databinding.FragmentOverviewBinding

@AndroidEntryPoint
class OverviewFragment : Fragment() {
    private lateinit var viewBinding: FragmentOverviewBinding
    private val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentOverviewBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OverviewAdapter(emptyList())
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewBinding.exchangeRatesRecycler.adapter = adapter
        viewBinding.exchangeRatesRecycler.layoutManager = layoutManager

        viewModel.isLoading().observe(viewLifecycleOwner) { loading ->
            adapter.setLoading(loading)
        }

        viewModel.getExchangeRates().observe(viewLifecycleOwner) { rates ->
            adapter.addItems(rates)
        }

        viewModel.loadData(listOf("AUD", "CAD", "CHF", "PLN", "USD"))
    }
}