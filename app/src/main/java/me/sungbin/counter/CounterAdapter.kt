package me.sungbin.counter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.sungbin.counter.databinding.LayoutCounterBinding


/**
 * Created by SungBin on 2021-01-05.
 */

class CounterAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<CounterAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: LayoutCounterBinding,
        private val context: Context,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private val count: Int get() = binding.tvCount.text.split(" ")[0].toInt()
        private fun setCount(count: Int) {
            binding.tvCount.text = context.getString(R.string.counter_count, count)
        }

        fun bindViewHolder(index: Int) {
            setCount(0)
            binding.tvIndex.text = index.toString()
            binding.ivClear.setOnClickListener { setCount(0) }

            binding.btnPlus1.setOnClickListener { setCount(count + 1) }
            binding.btnPlus10.setOnClickListener { setCount(count + 10) }
            binding.btnPlus100.setOnClickListener { setCount(count + 100) }
            binding.btnPlus1000.setOnClickListener { setCount(count + 1000) }

            binding.btnMinus1.setOnClickListener { setCount(count - 1) }
            binding.btnMinus10.setOnClickListener { setCount(count - 10) }
            binding.btnMinus100.setOnClickListener { setCount(count - 100) }
            binding.btnMinus1000.setOnClickListener { setCount(count - 1000) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutCounterBinding.inflate(LayoutInflater.from(parent.context)),
        parent.context
    )

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        holder.bindViewHolder(index)
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}
