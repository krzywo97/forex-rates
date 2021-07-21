package pl.makrohard.forexrates.presentation.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.makrohard.forexrates.databinding.OverviewRateItemBinding

class OverviewAdapter(private var items: List<String>) :
    RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    inner class ViewHolder(private val viewBinding: OverviewRateItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {
            viewBinding.currency.text = items[position]
            viewBinding.rate.text = items[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            OverviewRateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}