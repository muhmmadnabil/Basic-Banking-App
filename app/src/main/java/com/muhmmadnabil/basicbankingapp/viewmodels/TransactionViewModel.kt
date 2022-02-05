package com.muhmmadnabil.basicbankingapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.muhmmadnabil.basicbankingapp.data.TransactionDatabase
import com.muhmmadnabil.basicbankingapp.data.TransactionRepository
import com.muhmmadnabil.basicbankingapp.data.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    val getAllTransactions: LiveData<List<Transaction>>
    private val repository: TransactionRepository

    init {
        val transactionDao = TransactionDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(transactionDao)
        getAllTransactions = repository.getAllTransactions
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTransaction(transaction)
        }
    }
}