package pl.makrohard.forexrates.presentation.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.makrohard.forexrates.databinding.OverviewDateHeaderBinding
import pl.makrohard.forexrates.databinding.OverviewLoadingItemBinding
import pl.makrohard.forexrates.databinding.OverviewRateItemBinding
import pl.makrohard.forexrates.domain.model.DailyRates
import pl.makrohard.forexrates.domain.model.Rate

class OverviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_RATE = 0
        const val ITEM_DATE_HEADER = 1
        const val ITEM_PROGRESS_BAR = 2
    }

    private var itemsList = emptyList<Any>()
    private var isLoading = false

    inner class RateViewHolder(private val viewBinding: OverviewRateItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {
            val item = itemsList[position]
            if (item is Rate) {
                viewBinding.currency.text = item.currency
                viewBinding.rate.text = item.rate.toString()
            }
        }
    }

    inner class DateHeaderViewHolder(private val viewBinding: OverviewDateHeaderBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int) {
            val item = itemsList[position]
            if (item is String) {
                viewBinding.date.text = item
            }
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
            ITEM_DATE_HEADER -> {
                val viewBinding = OverviewDateHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DateHeaderViewHolder(viewBinding)
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

        if (holder is DateHeaderViewHolder) {
            holder.bind(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size + isLoading.compareTo(false) // compareTo(false) returns 0 if isLoading == false and returns 1 otherwise
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1 && isLoading) return ITEM_PROGRESS_BAR

        val item = itemsList[position]
        if (item is String) return ITEM_DATE_HEADER
        return ITEM_RATE
    }

    fun addItems(rates: List<DailyRates>) {
        for (r in rates) {
            val oldSize = this.itemsList.size
            this.itemsList += r.date
            this.itemsList += r.rates
            notifyItemRangeInserted(oldSize, r.rates.size + 1)
        }
    }

    fun setLoading(loading: Boolean) {
        if (isLoading == loading) return
        isLoading = loading

        if (loading) {
            notifyItemInserted(itemsList.size)
        } else {
            notifyItemRemoved(itemsList.size)
        }
    }
}