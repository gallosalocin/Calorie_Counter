package com.gallosalocin.calorie_counter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.models.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Food::class], version = 1, exportSchema = false)

abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    private class FoodDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var foodDao = database.foodDao()

                    foodDao.deleteAll()

                    foodDao.insertFood(Food("Blanc de poulet", "Protéines", 0xFFE57373.toInt(), "17 minutes", 162, 150, 2F, 0.7F, 35.3F))
                    foodDao.insertFood(Food("Jambon", "Protéines", 0xFFE57373.toInt(), "", 162, 150, 2F, 0.7F, 35.3F))
                    foodDao.insertFood(Food("Coco Plats", "Légumes", 0xFF48B34D.toInt(), "", 75, 250, 0.5F, 9.1F, 4.6F))
                    foodDao.insertFood(Food("Huile d'Olive", "Huiles", 0xFFC1A36E.toInt(), "", 90, 10, 10F, 0F, 0F))
                    foodDao.insertFood(Food("Feta", "Graisses saines", 0xFF4DD0E1.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
                    foodDao.insertFood(Food("Pomme", "Fruits", 0xFF9575CD.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
                    foodDao.insertFood(Food("Glace", "Glucides", 0xFFFFF176.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): FoodDatabase {
                return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "food_database"
                ).addCallback(FoodDatabaseCallback(scope))
                 .build()
                INSTANCE = instance
                instance
            }
        }
    }
}