package com.muhmmadnabil.basicbankingapp.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhmmadnabil.basicbankingapp.R
import com.muhmmadnabil.basicbankingapp.data.model.User
import com.muhmmadnabil.basicbankingapp.databinding.CustomRowBinding

class ListAdapter(private val context: Context) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivUser = binding.ivUser
        val tvName = binding.tvUserName
        val tvEmail = binding.tvUserEmail
        val tvCurrency = binding.tvUserCurrency
        val llButtons = binding.llButtons
        val btnDetails = binding.btnDetails
        val btnTransactions = binding.btnTransactions
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.llButtons.visibility = View.GONE
        with(userList[position]) {
            Glide.with(context)
                .load(this.image)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(holder.ivUser)

            holder.tvName.text = this.name
            holder.tvEmail.text = this.email
            holder.tvCurrency.text = "${this.currency}$"

            holder.itemView.setOnClickListener {
                holder.llButtons.visibility = View.VISIBLE
            }

            holder.btnDetails.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("USER", this)
                Navigation.createNavigateOnClickListener(
                    R.id.action_listFragment_to_detailsFragment,
                    bundle
                ).onClick(it)
            }

            holder.btnTransactions.setOnClickListener {
                Navigation.createNavigateOnClickListener(R.id.action_listFragment_to_transactionFragment)
                    .onClick(it)
            }


        }
    }

    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }
}