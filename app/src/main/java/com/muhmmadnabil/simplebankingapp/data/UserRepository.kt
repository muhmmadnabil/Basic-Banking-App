package com.muhmmadnabil.simplebankingapp.data

import androidx.lifecycle.LiveData
import com.muhmmadnabil.simplebankingapp.data.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

}