package com.muhmmadnabil.basicbankingapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.muhmmadnabil.basicbankingapp.data.model.User

class Converters {
    @TypeConverter
    fun fromUserToString(user: User): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun fromStringTOUser(user: String): User {
        return Gson().fromJson(user, User::class.java)
    }
}