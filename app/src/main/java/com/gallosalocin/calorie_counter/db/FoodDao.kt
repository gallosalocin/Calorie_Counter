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

    @Query("SELECT * FROM food_table ORDER BY lower(name)")
    fun getAllFoodsSortedByName(): LiveData<List<Food>>

    @Query("SELECT * FROM food_table ORDER BY category ASC")
    fun getAllFoodsSortedByCategory(): LiveData<List<Food>>

    @Query("SELECT * FROM food_table WHERE category = '2131624049'")
    fun getAllFoodsFilteredByProtein(): LiveData<List<Food>>

}