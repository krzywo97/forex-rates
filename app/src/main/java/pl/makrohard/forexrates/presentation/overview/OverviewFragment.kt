package pl.makrohard.forexrates.presentation.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.makrohard.forexrates.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {
    private lateinit var viewBinding: FragmentOverviewBinding

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

        val adapter = OverviewAdapter(listOf("a", "b"))
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewBinding.exchangeRatesRecycler.adapter = adapter
        viewBinding.exchangeRatesRecycler.layoutManager = layoutManager
    }
}