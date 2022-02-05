package com.muhmmadnabil.simplebankingapp.data

import com.muhmmadnabil.simplebankingapp.data.model.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {
    val getAllTransactions = transactionDao.getAllTransactions()

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }
}