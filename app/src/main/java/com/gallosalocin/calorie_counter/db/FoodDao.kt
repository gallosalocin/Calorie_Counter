package com.gallosalocin.calorie_counter.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gallosalocin.calorie_counter.models.Food

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("DELETE FROM food_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM food_table WHERE day_meal_id = '0' ORDER BY lower(name)")
    fun getAllFoodsSortedByName(): LiveData<List<Food>>

    @Query("SELECT * FROM food_table WHERE day_meal_id = '0' ORDER BY category, lower(name)")
    fun getAllFoodsSortedByCategory(): LiveData<List<Food>>

    @Query("SELECT * FROM food_table WHERE day_meal_id = '0' AND category = :category ORDER BY lower(name)")
    fun getAllFoodsPerCategory(category: String): LiveData<List<Food>>

    @Query("SELECT * FROM food_table WHERE day_meal_id = :dayMeal")
    fun getAllFoodsSortedByDayAndMeal(dayMeal: String): LiveData<List<Food>>

    @Query("INSERT INTO food_table(day_meal_id, name, category, color, note, calorie, weight, fat, carb, prot) SELECT :dayMeal, name, category, color, note, calorie, weight, fat, carb, prot FROM food_table WHERE id = :id")
    suspend fun duplicateFood(dayMeal: String, id: Int?)

    @Query("SELECT * FROM food_table WHERE day_meal_id = :dayMeal")
    fun getAllFoodsSortedByBreakfast(dayMeal: String): LiveData<List<Food>>

    @Query("SELECT * FROM food_table WHERE day_meal_id = :dayMeal")
    fun getAllFoodsSortedByLunch(dayMeal: String): LiveData<List<Food>>

    @Query("SELECT * FROM food_table WHERE day_meal_id = :dayMeal")
    fun getAllFoodsSortedByDinner(dayMeal: String): LiveData<List<Food>>

    @Query("SELECT * FROM food_table WHERE day_meal_id = :dayMeal")
    fun getAllFoodsSortedBySnack(dayMeal: String): LiveData<List<Food>>

    @Query("SELECT * FROM food_table WHERE day_meal_id BETWEEN :dayMealStart AND :dayMealEnd")
    fun getAllFoodsMacrosTotal(dayMealStart: String, dayMealEnd: String): LiveData<List<Food>>

}