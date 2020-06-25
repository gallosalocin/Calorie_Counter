package com.gallosalocin.calorie_counter.repository

import androidx.lifecycle.LiveData
import com.gallosalocin.calorie_counter.db.FoodDao
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.ui.DayActivity
import com.gallosalocin.calorie_counter.ui.MainActivity
import com.gallosalocin.calorie_counter.ui.SearchActivity

class FoodRepository(private val foodDao: FoodDao) {

    val allFoods: LiveData<List<Food>> = foodDao.getAllFoodsSortedByName()
    val allFoodsByCategory: LiveData<List<Food>> = foodDao.getAllFoodsSortedByCategory()
    val allFoodsByProtein: LiveData<List<Food>> = foodDao.getAllFoodsFilteredByProtein()

    val allFoodsSortedByDayAndMeal: LiveData<List<Food>> = foodDao.getAllFoodsSortedByDayAndMeal(MainActivity.dayTag.toString() + DayActivity.mealTag.toString())

    suspend fun insertFood(food: Food) = foodDao.insertFood(food)

    suspend fun updateFood(food: Food) = foodDao.updateFood(food)

    suspend fun deleteFood(food: Food) = foodDao.deleteFood(food)

    suspend fun duplicateFood(dayMeal: String, id: Int?) = foodDao.duplicateFood(dayMeal, id)

}