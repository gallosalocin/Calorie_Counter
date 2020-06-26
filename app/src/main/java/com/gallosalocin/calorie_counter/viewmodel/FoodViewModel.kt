package com.gallosalocin.calorie_counter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gallosalocin.calorie_counter.db.FoodDatabase
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FoodRepository

    val allFoods: LiveData<List<Food>>
    val allFoodsByCategory: LiveData<List<Food>>

    val allFoodsCategoryProteins: LiveData<List<Food>>
    val allFoodsCategoryCarbs: LiveData<List<Food>>
    val allFoodsCategoryVeggies: LiveData<List<Food>>
    val allFoodsCategoryFruits: LiveData<List<Food>>
    val allFoodsCategoryHealthyfats: LiveData<List<Food>>
    val allFoodsCategoryOils: LiveData<List<Food>>


    val allFoodsByDayAndMeal: LiveData<List<Food>>
    val allFoodsByBreakfast: LiveData<List<Food>>
    val allFoodsByLunch: LiveData<List<Food>>
    val allFoodsByDinner: LiveData<List<Food>>
    val allFoodsBySnack: LiveData<List<Food>>
    val allFoodsMacrosTotal: LiveData<List<Food>>

    init {
        val foodDao = FoodDatabase.getDatabase(application, viewModelScope).foodDao()
        repository = FoodRepository(foodDao)
        allFoods = repository.allFoods
        allFoodsByCategory = repository.allFoodsByCategory

        allFoodsCategoryProteins = repository.allFoodsCategoryProteins
        allFoodsCategoryCarbs = repository.allFoodsCategoryCarbs
        allFoodsCategoryVeggies = repository.allFoodsCategoryVeggies
        allFoodsCategoryFruits = repository.allFoodsCategoryFruits
        allFoodsCategoryHealthyfats = repository.allFoodsCategoryHealthyFats
        allFoodsCategoryOils = repository.allFoodsCategoryOils


        allFoodsByDayAndMeal = repository.allFoodsSortedByDayAndMeal
        allFoodsByBreakfast = repository.allFoodsSortedByBreakfast
        allFoodsByLunch = repository.allFoodsSortedByLunch
        allFoodsByDinner = repository.allFoodsSortedByDinner
        allFoodsBySnack = repository.allFoodsSortedBySnack
        allFoodsMacrosTotal = repository.allFoodsMacrosTotal
    }

    fun insertFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFood(food)
    }

    fun updateFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateFood(food)
    }

    fun deleteFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFood(food)
    }

    fun duplicateFood(dayMeal: String, id: Int?) = viewModelScope.launch(Dispatchers.IO) {
        repository.duplicateFood(dayMeal, id)
    }
}