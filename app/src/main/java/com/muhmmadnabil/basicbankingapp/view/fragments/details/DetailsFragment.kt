package com.muhmmadnabil.basicbankingapp.view.fragments.details

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.muhmmadnabil.basicbankingapp.R
import com.muhmmadnabil.basicbankingapp.adapters.TransactionAdapter
import com.muhmmadnabil.basicbankingapp.data.model.User
import com.muhmmadnabil.basicbankingapp.databinding.FragmentDetailsBinding
import com.muhmmadnabil.basicbankingapp.util.Constants
import com.muhmmadnabil.basicbankingapp.viewmodels.TransactionViewModel
import com.muhmmadnabil.basicbankingapp.viewmodels.UserViewModel

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val TAG = "DetailsFragment"
    private lateinit var uri: Uri
    private lateinit var userViewModel: UserViewModel
    private lateinit var transactionViewModel: TransactionViewModel
    private var id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]

        val user = arguments?.getSerializable("USER") as User

        id = user.id
        uri = (user.image)!!.toUri()

        Glide.with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(binding.ivImage)

        binding.etName.setText(user.name)
        binding.etEmail.setText(user.email)
        binding.etMoney.setText(user.currency.toString())

        binding.tvNoTransactions.visibility = View.VISIBLE

        binding.ivImage.setOnClickListener {
            imageChooser()
        }

        binding.btnUpdate.setOnClickListener {
            updateDatabase()
        }

        val adapter = TransactionAdapter()
        binding.rvTransactions.adapter = adapter
        binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())

        transactionViewModel.getAllTransactions.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)

            if (adapter.list.isEmpty()) {
                binding.tvPreviousTransactions.visibility = View.GONE
                binding.tvRecyclerHelper.visibility = View.GONE
                binding.rvTransactions.visibility = View.GONE
                binding.tvNoTransactions.visibility = View.VISIBLE
            } else {
                binding.tvPreviousTransactions.visibility = View.VISIBLE
                binding.tvRecyclerHelper.visibility = View.VISIBLE
                binding.rvTransactions.visibility = View.VISIBLE
                binding.tvNoTransactions.visibility = View.GONE
            }

        })

        return binding.root
    }

    private fun imageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            Constants.SELECT_PICTURE
        )
    }

    private fun updateDatabase() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val money = binding.etMoney.text

        if (inputCheck(name, email, money)) {
            val user = User(id!!, name, email, uri.toString(), money.toString().toDouble())
            userViewModel.updateUser(user)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_detailsFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.SELECT_PICTURE) {
                uri = data?.data!!

                Log.i(TAG, "The image is ${uri}")

                Glide.with(this)
                    .load(uri)
                    .centerCrop()
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(binding.ivImage)
            }
        }
    }

}