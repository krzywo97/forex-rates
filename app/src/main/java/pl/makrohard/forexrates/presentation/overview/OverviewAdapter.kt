package pl.makrohard.forexrates.presentation.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.makrohard.forexrates.databinding.OverviewRateItemBinding
import pl.makrohard.forexrates.domain.model.Rate

class OverviewAdapter(private var items: List<Rate>) :
    RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    inner class ViewHolder(private val viewBinding: OverviewRateItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {
            viewBinding.currency.text = items[position].currency
            viewBinding.rate.text = items[position].rate.toString()
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

    fun addItems(items: List<Rate>) {
        val oldSize = this.items.size
        this.items += items
        notifyItemRangeInserted(oldSize, items.size)
    }
}