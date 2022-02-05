package com.muhmmadnabil.simplebankingapp.view.fragments.transaction

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhmmadnabil.simplebankingapp.R
import com.muhmmadnabil.simplebankingapp.adapters.CustomListItemAdapter
import com.muhmmadnabil.simplebankingapp.data.model.Transaction
import com.muhmmadnabil.simplebankingapp.data.model.User
import com.muhmmadnabil.simplebankingapp.databinding.DialogCustomListBinding
import com.muhmmadnabil.simplebankingapp.databinding.FragmentTransactionBinding
import com.muhmmadnabil.simplebankingapp.util.Constants.FROM
import com.muhmmadnabil.simplebankingapp.util.Constants.TO
import com.muhmmadnabil.simplebankingapp.viewmodels.TransactionViewModel
import com.muhmmadnabil.simplebankingapp.viewmodels.UserViewModel

class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactionBinding
    private lateinit var customListDialog: Dialog
    private lateinit var userViewModel: UserViewModel
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var users: List<User>
    private lateinit var fromUser: User
    private lateinit var toUser: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(layoutInflater)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]

        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { users ->
            this.users = users
        })

        binding.etFrom.setOnClickListener {
            customItemListDialog(users, FROM)
        }

        binding.etTo.setOnClickListener {
            customItemListDialog(users, TO)
        }

        binding.btnSendMoney.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }


    private fun customItemListDialog(list: List<User>, type: String) {
        customListDialog = Dialog(requireContext())

        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)
        customListDialog.setContentView(binding.root)
        binding.tvTitle.text = "Bank Users"
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CustomListItemAdapter(this, list, type)
        binding.rvList.adapter = adapter
        customListDialog.show()

    }

    fun selectUser(user: User, type: String) {
        customListDialog.dismiss()
        if (type == FROM) {
            fromUser = user
            binding.etFrom.setText(user.name)
        } else {
            toUser = user
            binding.etTo.setText(user.name)
        }
    }

    private fun insertDataToDatabase() {
        val etFrom = binding.etFrom.text.toString()
        val etTo = binding.etTo.text.toString()
        val money = binding.etMoney.text

        if (inputCheck(etFrom, etTo, money)) {

            fromUser.currency -= money.toString().toDouble()
            toUser.currency += money.toString().toDouble()

            val transaction = Transaction(0, fromUser, money.toString().toDouble(), toUser)

            transactionViewModel.insertTransaction(transaction)
            userViewModel.updateUser(fromUser)
            userViewModel.updateUser(toUser)
            Toast.makeText(requireContext(), "Successfully sent!", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_transactionFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(fromUser: String, toUser: String, money: Editable): Boolean {
        return !(TextUtils.isEmpty(fromUser) && TextUtils.isEmpty(toUser) && money.isEmpty())
    }

}