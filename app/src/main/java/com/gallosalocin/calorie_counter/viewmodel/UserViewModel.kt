package com.gallosalocin.calorie_counter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gallosalocin.calorie_counter.db.FoodDatabase
import com.gallosalocin.calorie_counter.models.User
import com.gallosalocin.calorie_counter.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    val getUser: LiveData<User>


    init {
        val userDao = FoodDatabase.getDatabase(application, viewModelScope).userDao()
        repository = UserRepository(userDao)

        getUser = repository.getUser
    }

    fun upsertUser(user: User) = viewModelScope.launch(Dispatchers.IO) { repository.upsertUser(user) }
}