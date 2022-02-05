package com.muhmmadnabil.basicbankingapp.view.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.muhmmadnabil.basicbankingapp.R
import com.muhmmadnabil.basicbankingapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        binding.btnDisplay.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_listFragment)
        }

        binding.btnTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_transactionFragment)
        }

        return binding.root
    }
}