package com.gallosalocin.calorie_counter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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

                    foodDao.insertFood(Food("0","Blanc de poulet", "Protéines", 0xFFE57373.toInt(), "", 108, 100, 1.34F, 0.45F, 23.5F))
                    foodDao.insertFood(Food("0","Jambon", "Protéines", 0xFFE57373.toInt(), "", 108, 100, 3F, 1.2F, 19F))
                    foodDao.insertFood(Food("0","Coco Plats", "Légumes", 0xFF48B34D.toInt(), "", 30, 100, 0.2F, 3.6F, 1.9F))
                    foodDao.insertFood(Food("0","Huile d'Olive", "Huiles", 0xFFC1A36E.toInt(), "", 900, 100, 100F, 0F, 0F))
                    foodDao.insertFood(Food("0","Feta", "Graisses saines", 0xFF4DD0E1.toInt(), "", 257, 100, 21F, 0.7F, 17F))
                    foodDao.insertFood(Food("0","Pomme", "Fruits", 0xFF9575CD.toInt(), "", 52, 100, 0.2F, 14F, 0.3F))
                    foodDao.insertFood(Food("0","Glace Vanille", "Glucides", 0xFFFFF176.toInt(), "", 190, 100, 8.7F, 26F, 2.2F))

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