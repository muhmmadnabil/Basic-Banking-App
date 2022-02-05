package com.muhmmadnabil.basicbankingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhmmadnabil.basicbankingapp.data.model.User
import com.muhmmadnabil.basicbankingapp.databinding.ItemCustomListLayoutBinding
import com.muhmmadnabil.basicbankingapp.view.fragments.transaction.TransactionFragment

class CustomListItemAdapter(
    private val fragment: TransactionFragment,
    private val list: List<User>,
    private val type: String
) :
    RecyclerView.Adapter<CustomListItemAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemCustomListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvText = binding.tvText
        val tvId = binding.tvId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCustomListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvText.text = item.name
        holder.tvId.text = item.id.toString()

        holder.itemView.setOnClickListener {
            fragment.selectUser(item, type)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}