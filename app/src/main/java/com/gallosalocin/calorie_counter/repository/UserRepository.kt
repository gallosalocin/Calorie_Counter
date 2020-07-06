package com.gallosalocin.calorie_counter.repository

import androidx.lifecycle.LiveData
import com.gallosalocin.calorie_counter.db.UserDao
import com.gallosalocin.calorie_counter.models.User

class UserRepository(private val userDao: UserDao) {

    val getUser: LiveData<User> = userDao.getUser()

    suspend fun upsertUser(user: User) = userDao.upsertUser(user)

}