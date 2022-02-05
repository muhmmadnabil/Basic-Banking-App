package com.muhmmadnabil.simplebankingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val fromUser: User,
    val money: Double,
    val toUser: User
)
