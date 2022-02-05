package com.muhmmadnabil.basicbankingapp.view.fragments.add

import android.app.Activity.RESULT_OK
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.muhmmadnabil.basicbankingapp.R
import com.muhmmadnabil.basicbankingapp.data.model.User
import com.muhmmadnabil.basicbankingapp.databinding.FragmentAddBinding
import com.muhmmadnabil.basicbankingapp.util.Constants.SELECT_PICTURE
import com.muhmmadnabil.basicbankingapp.viewmodels.UserViewModel

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var userViewModel: UserViewModel
    private var uri: Uri? = null
    private val TAG = "AddFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.btnPick.setOnClickListener {
            imageChooser()
        }

        binding.btnAdd.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun imageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
    }

    private fun insertDataToDatabase() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val money = binding.etMoney.text

        if (inputCheck(name, email, money)) {
            val user = User(0, name, email, uri.toString(), money.toString().toDouble())
            userViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
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
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                uri = data?.data!!

                Log.i(TAG, "The image is ${uri}")

                Glide.with(this)
                    .load(uri)
                    .centerCrop()
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(binding.ivUserImage)
            }
        }
    }

}