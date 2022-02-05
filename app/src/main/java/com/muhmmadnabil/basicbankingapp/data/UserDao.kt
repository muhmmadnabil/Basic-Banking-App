package com.muhmmadnabil.basicbankingapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.muhmmadnabil.basicbankingapp.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

}