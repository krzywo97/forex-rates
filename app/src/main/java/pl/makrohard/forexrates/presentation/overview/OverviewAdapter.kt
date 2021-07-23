package pl.makrohard.forexrates.presentation.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.makrohard.forexrates.databinding.OverviewLoadingItemBinding
import pl.makrohard.forexrates.databinding.OverviewRateItemBinding
import pl.makrohard.forexrates.domain.model.Rate

class OverviewAdapter(private var items: List<Rate>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_RATE = 0
        const val ITEM_LOADING = 1
    }

    private var isLoading = false

    inner class RateViewHolder(private val viewBinding: OverviewRateItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {
            viewBinding.currency.text = items[position].currency
            viewBinding.rate.text = items[position].rate.toString()
        }
    }

    inner class LoadingViewHolder(viewBinding: OverviewLoadingItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_RATE -> {
                val viewBinding = OverviewRateItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return RateViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = OverviewLoadingItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LoadingViewHolder(viewBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RateViewHolder) {
            holder.bind(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return items.size + isLoading.compareTo(false) // compareTo(false) returns 0 if isLoading == false and returns 1 otherwise
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1 && isLoading) return ITEM_LOADING

        return ITEM_RATE
    }

    fun addItems(items: List<Rate>) {
        val oldSize = this.items.size
        this.items += items
        notifyItemRangeInserted(oldSize, items.size)
    }

    fun setLoading(loading: Boolean) {
        if (this.isLoading == loading) return

        if (loading) {
            notifyItemInserted(items.size)
        } else {
            notifyItemRemoved(items.size)
        }
    }
}