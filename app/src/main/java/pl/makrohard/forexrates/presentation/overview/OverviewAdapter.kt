package pl.makrohard.forexrates.presentation.overview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.makrohard.forexrates.databinding.OverviewDateHeaderBinding
import pl.makrohard.forexrates.databinding.OverviewLoadingItemBinding
import pl.makrohard.forexrates.databinding.OverviewRateItemBinding
import pl.makrohard.forexrates.domain.model.DailyRates
import pl.makrohard.forexrates.domain.model.Rate
import pl.makrohard.forexrates.presentation.home.MainInteractor

class OverviewAdapter(private val mainInteractor: MainInteractor) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_RATE = 0
        const val ITEM_DATE_HEADER = 1
        const val ITEM_PROGRESS_BAR = 2
    }

    private var itemsList = emptyList<Any>()
    var isLoading = false
        private set

    inner class RateViewHolder(private val viewBinding: OverviewRateItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val item = itemsList[position]
            if (item is Rate) {
                viewBinding.root.setOnClickListener {
                    var date = ""
                    for (i in position downTo 0) {
                        if (itemsList[i] is String) {
                            date = itemsList[i] as String
                            break
                        }
                    }

                    mainInteractor.showDetailFragment(date, item.currency, item.rate)
                }
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

    inner class DiffCallback(private val oldList: List<Any>, private val newList: List<Any>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            if (oldItem == newItem) return true
            if (oldItem is DailyRates && newItem is DailyRates) {
                if (oldItem.date == newItem.date) return true
            }
            return false
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areItemsTheSame(
                oldItemPosition,
                newItemPosition
            ) // items are never changed once created
        }
    }

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
        return itemsList.size + isLoading.compareTo(false)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1 && isLoading) return ITEM_PROGRESS_BAR

        val item = itemsList[position]
        if (item is String) return ITEM_DATE_HEADER
        return ITEM_RATE
    }

    suspend fun setItems(rates: List<DailyRates>) {
        val newItemsList = ArrayList<Any>()
        for (r in rates) {
            newItemsList.add(r.date)
            newItemsList.addAll(r.rates)
        }

        withContext(Dispatchers.Default) {
            val diffResult = DiffUtil.calculateDiff(DiffCallback(itemsList, newItemsList))
            itemsList = newItemsList
            withContext(Dispatchers.Main) {
                diffResult.dispatchUpdatesTo(this@OverviewAdapter)
            }
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