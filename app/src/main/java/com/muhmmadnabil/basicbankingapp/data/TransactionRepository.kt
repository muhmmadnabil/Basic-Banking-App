package com.muhmmadnabil.basicbankingapp.data

import com.muhmmadnabil.basicbankingapp.data.model.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {
    val getAllTransactions = transactionDao.getAllTransactions()

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }
}