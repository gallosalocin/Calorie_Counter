package com.gallosalocin.calorie_counter.repository

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveData
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.db.FoodDao
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.ui.DayActivity
import com.gallosalocin.calorie_counter.ui.MainActivity

class FoodRepository(private val foodDao: FoodDao) {


    val allFoods: LiveData<List<Food>> = foodDao.getAllFoodsSortedByName()
    val allFoodsByCategory: LiveData<List<Food>> = foodDao.getAllFoodsSortedByCategory()

    val allFoodsCategoryProteins: LiveData<List<Food>> = foodDao.getAllFoodsPerCategory("Protéines")    //  context.getString(R.string.proteins)
    val allFoodsCategoryCarbs: LiveData<List<Food>> = foodDao.getAllFoodsPerCategory("Glucides")
    val allFoodsCategoryVeggies: LiveData<List<Food>> = foodDao.getAllFoodsPerCategory("Légumes")
    val allFoodsCategoryFruits: LiveData<List<Food>> = foodDao.getAllFoodsPerCategory("Fruits")
    val allFoodsCategoryHealthyFats: LiveData<List<Food>> = foodDao.getAllFoodsPerCategory("Graisses saines")
    val allFoodsCategoryOils: LiveData<List<Food>> = foodDao.getAllFoodsPerCategory("Huiles")

    val allFoodsSortedByDayAndMeal: LiveData<List<Food>> = foodDao.getAllFoodsSortedByDayAndMeal(MainActivity.dayTag.toString() + DayActivity.mealTag.toString())
    val allFoodsSortedByBreakfast: LiveData<List<Food>> = foodDao.getAllFoodsSortedByMeal(MainActivity.dayTag.toString() + "1")
    val allFoodsSortedByLunch: LiveData<List<Food>> = foodDao.getAllFoodsSortedByMeal(MainActivity.dayTag.toString() + "2")
    val allFoodsSortedByDinner: LiveData<List<Food>> = foodDao.getAllFoodsSortedByMeal(MainActivity.dayTag.toString() + "3")
    val allFoodsSortedBySnack: LiveData<List<Food>> = foodDao.getAllFoodsSortedByMeal(MainActivity.dayTag.toString() + "4")
    val allFoodsMacrosTotal: LiveData<List<Food>> = foodDao.getAllFoodsMacrosTotal(MainActivity.dayTag.toString() + "1", MainActivity.dayTag.toString() + "4")

    suspend fun insertFood(food: Food) = foodDao.insertFood(food)

    suspend fun updateFood(food: Food) = foodDao.updateFood(food)

    suspend fun deleteFood(food: Food) = foodDao.deleteFood(food)

    suspend fun duplicateFood(dayMeal: String, id: Int?) = foodDao.duplicateFood(dayMeal, id)

}