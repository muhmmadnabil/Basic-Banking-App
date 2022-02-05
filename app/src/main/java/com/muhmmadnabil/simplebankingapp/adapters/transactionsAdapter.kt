package com.muhmmadnabil.simplebankingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhmmadnabil.simplebankingapp.data.model.Transaction
import com.muhmmadnabil.simplebankingapp.databinding.RecyclerViewItemBinding

class TransactionAdapter :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    lateinit var list: List<Transaction>

    class ViewHolder(binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvFrom = binding.tvFrom
        val tvMoney = binding.tvMoney
        val tvTo = binding.tvTo
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFrom.text = list[position].fromUser.name
        holder.tvMoney.text = list[position].money.toString()
        holder.tvTo.text = list[position].toUser.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: List<Transaction>) {
        this.list = list
        notifyDataSetChanged()
    }
}