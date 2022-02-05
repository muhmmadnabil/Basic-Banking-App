package com.muhmmadnabil.basicbankingapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muhmmadnabil.basicbankingapp.data.model.Transaction

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_table")
    fun getAllTransactions(): LiveData<List<Transaction>>
}