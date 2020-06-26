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

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val foodDao = database.foodDao()

                    foodDao.deleteAll()

                    foodDao.insertFood(Food("0","Blanc de poulet", "Protéines", 0xFFE57373.toInt(), "", 108F, 100, 1.34F, 0.45F, 23.5F))
                    foodDao.insertFood(Food("0","Jambon cuit à l'étouffée", "Protéines", 0xFFE57373.toInt(), "", 108F, 100, 3F, 1.2F, 19F))
                    foodDao.insertFood(Food("0","Bar", "Protéines", 0xFFE57373.toInt(), "500g - 10mn", 96F, 100, 1.6F, 0.3F, 20.1F))
                    foodDao.insertFood(Food("0","Blanc d'oeuf", "Protéines", 0xFFE57373.toInt(), "", 47F, 100, 0.17F, 1.12F, 10.3F))
                    foodDao.insertFood(Food("0","Boeuf haché 5%", "Protéines", 0xFFE57373.toInt(), "", 130F, 100, 4.6F, 0.3F, 21.9F))
                    foodDao.insertFood(Food("0","Fromage blanc carrefour", "Protéines", 0xFFE57373.toInt(), "", 71F, 100, 3F, 4.5F, 6.5F))
                    foodDao.insertFood(Food("0","Lait demi écrémé", "Protéines", 0xFFE57373.toInt(), "", 46F, 100, 1.6F, 4.8F, 3.2F))
                    foodDao.insertFood(Food("0","Oeuf", "Protéines", 0xFFE57373.toInt(), "", 134F, 100, 8.6F, 0.5F, 13.5F))
                    foodDao.insertFood(Food("0","Whey protein O.N.", "Protéines", 0xFFE57373.toInt(), "", 372F, 100, 3.8F, 5.8F, 78.5F))
                    foodDao.insertFood(Food("0","Whey protein Sci-MX", "Protéines", 0xFFE57373.toInt(), "", 396F, 100, 6.6F, 5.9F, 74F))
                    foodDao.insertFood(Food("0","Thon entier au naturel", "Protéines", 0xFFE57373.toInt(), "", 122F, 100, 2F, 0.1F, 26F))
                    foodDao.insertFood(Food("0","Crevettes", "Protéines", 0xFFE57373.toInt(), "", 113F, 100, 0.6F, 0.2F, 26.6F))
                    foodDao.insertFood(Food("0","Huile d'olive", "Huiles", 0xFFC1A36E.toInt(), "", 900F, 100, 100F, 0F, 0F))
                    foodDao.insertFood(Food("0","Beurre de cacahuètes", "Huiles", 0xFFC1A36E.toInt(), "", 618F, 100, 50F, 7F, 30F))
                    foodDao.insertFood(Food("0","Tahini", "Huiles", 0xFFC1A36E.toInt(), "", 677F, 100, 62.1F, 1.3F, 23.7F))
                    foodDao.insertFood(Food("0","Olives", "Huiles", 0xFFC1A36E.toInt(), "", 233F, 100, 22.6F, 4.3F, 1.6F))
                    foodDao.insertFood(Food("0","Féta Dodonis", "Graisses saines", 0xFF4DD0E1.toInt(), "", 257F, 100, 21F, 0.7F, 17F))
                    foodDao.insertFood(Food("0","Amandes", "Graisses saines", 0xFF4DD0E1.toInt(), "", 634F, 100, 53.4F, 7.9F, 25.4F))
                    foodDao.insertFood(Food("0","Avocat", "Graisses saines", 0xFF4DD0E1.toInt(), "", 160F, 100, 14.7F, 8.5F, 2F))
                    foodDao.insertFood(Food("0","Bûche de chèvre", "Graisses saines", 0xFF4DD0E1.toInt(), "", 290F, 100, 23F, 1.4F, 20F))
                    foodDao.insertFood(Food("0","Comté", "Graisses saines", 0xFF4DD0E1.toInt(), "", 410F, 100, 34F, 0F, 26F))
                    foodDao.insertFood(Food("0","Chocolat Noir 72%", "Graisses saines", 0xFF4DD0E1.toInt(), "", 599F, 100, 46F, 33F, 8.5F))
                    foodDao.insertFood(Food("0","Chocolat Noir 80%", "Graisses saines", 0xFF4DD0E1.toInt(), "", 622F, 100, 51F, 26F, 9.5F))
                    foodDao.insertFood(Food("0","Noisettes crues", "Graisses saines", 0xFF4DD0E1.toInt(), "", 628F, 100, 61F, 17F, 15F))
                    foodDao.insertFood(Food("0","Noix", "Graisses saines", 0xFF4DD0E1.toInt(), "", 698F, 100, 64F, 11F, 15F))
                    foodDao.insertFood(Food("0","Raclette", "Graisses saines", 0xFF4DD0E1.toInt(), "", 330F, 100, 26F, 1F, 23F))
                    foodDao.insertFood(Food("0","Coco plats", "Légumes", 0xFF48B34D.toInt(), "", 30F, 100, 0.21F, 3.63F, 1.85F))
                    foodDao.insertFood(Food("0","Asperges mini", "Légumes", 0xFF48B34D.toInt(), "", 17F, 100, 0F, 1.6F, 1.9F))
                    foodDao.insertFood(Food("0","Broccoli", "Légumes", 0xFF48B34D.toInt(), "", 35F,100, 0.41F, 7.18F, 2.38F))
                    foodDao.insertFood(Food("0","Carotte", "Légumes", 0xFF48B34D.toInt(), "", 34F, 100, 0.4F, 7.7F, 0.5F))
                    foodDao.insertFood(Food("0","Chou fleur", "Légumes", 0xFF48B34D.toInt(), "", 25F, 100, 0.3F, 5F, 1.9F))
                    foodDao.insertFood(Food("0","Concombre", "Légumes", 0xFF48B34D.toInt(), "", 14F, 100, 0.1F, 2F, 0.6F))
                    foodDao.insertFood(Food("0","Courgette", "Légumes", 0xFF48B34D.toInt(), "", 17F, 100, 0.32F, 3.1F, 1.2F))
                    foodDao.insertFood(Food("0","Laitue", "Légumes", 0xFF48B34D.toInt(), "", 15F, 100, 0.2F, 1.3F, 1.3F))
                    foodDao.insertFood(Food("0","Oignon", "Légumes", 0xFF48B34D.toInt(), "", 39F, 100, 0.6F, 6.2F, 1.1F))
                    foodDao.insertFood(Food("0","Poivron rouge", "Légumes", 0xFF48B34D.toInt(), "", 29F, 100, 0.35F,4.5F, 1.12F))
                    foodDao.insertFood(Food("0","Tomate cerise", "Légumes", 0xFF48B34D.toInt(), "", 21F, 100, 0.4F,3F, 0.8F))
                    foodDao.insertFood(Food("0","Pomme", "Fruits", 0xFF9575CD.toInt(), "", 52F, 100, 0.2F, 14F, 0.3F))
                    foodDao.insertFood(Food("0","Ananas", "Fruits", 0xFF9575CD.toInt(), "", 53F, 100, 0.24F, 11F, 0.52F))
                    foodDao.insertFood(Food("0","Abricot", "Fruits", 0xFF9575CD.toInt(), "", 48F, 100, 0.4F, 11.1F, 1.4F))
                    foodDao.insertFood(Food("0","Banane", "Fruits", 0xFF9575CD.toInt(), "", 90F, 100, 0.25F, 19.6F, 0.98F))
                    foodDao.insertFood(Food("0","Cerise", "Fruits", 0xFF9575CD.toInt(), "", 60F, 100, 0.25F, 11.6F, 1.16F))
                    foodDao.insertFood(Food("0","Clémentine", "Fruits", 0xFF9575CD.toInt(), "",47F, 100, 0.2F, 12F, 0.9F))
                    foodDao.insertFood(Food("0","Datte Medjool", "Fruits", 0xFF9575CD.toInt(), "",277F, 100, 0.2F, 75F, 1.8F))
                    foodDao.insertFood(Food("0","Nectarine", "Fruits", 0xFF9575CD.toInt(), "",44F, 100, 0.32F, 10.55F, 1.06F))
                    foodDao.insertFood(Food("0","Pêche", "Fruits", 0xFF9575CD.toInt(), "",39F, 100, 0.33F, 9F, 1.08F))
                    foodDao.insertFood(Food("0","Prune", "Fruits", 0xFF9575CD.toInt(), "",49F, 100, 0.29F, 9.41F, 0.66F))
                    foodDao.insertFood(Food("0","Raisin jumbo", "Fruits", 0xFF9575CD.toInt(), "",299F, 100, 1F, 79F, 3.1F))
                    foodDao.insertFood(Food("0","Glace vanille", "Glucides", 0xFFFFF176.toInt(), "", 190F, 100, 8.7F, 26F, 2.2F))
                    foodDao.insertFood(Food("0","Glace vanille & macadamia", "Glucides", 0xFFFFF176.toInt(), "", 209F, 100, 8.1F, 31F, 2.7F))
                    foodDao.insertFood(Food("0","Glace café", "Glucides", 0xFFFFF176.toInt(), "", 227F, 100, 9.3F, 32F, 3.5F))
                    foodDao.insertFood(Food("0","Bière", "Glucides", 0xFFFFF176.toInt(), "", 40F, 100, 0F, 3.6F, 0.5F))
                    foodDao.insertFood(Food("0","Biscotte blé complet", "Glucides", 0xFFFFF176.toInt(), "", 408F, 100, 10F, 66F, 10F))
                    foodDao.insertFood(Food("0","Biscotte Crétois", "Glucides", 0xFFFFF176.toInt(), "", 376F, 100, 5.2F, 69F, 13.4F))
                    foodDao.insertFood(Food("0","Burger", "Glucides", 0xFFFFF176.toInt(), "", 289F, 100, 5.9F, 48F, 9.7F))
                    foodDao.insertFood(Food("0","Flocons d'avoine Prozis", "Glucides", 0xFFFFF176.toInt(), "", 363F, 100, 6.6F, 59F, 12F))
                    foodDao.insertFood(Food("0","Gaufre au miel", "Glucides", 0xFFFFF176.toInt(), "", 481F, 100, 20F, 69F, 4.8F))
                    foodDao.insertFood(Food("0","Miel", "Glucides", 0xFFFFF176.toInt(), "", 301F, 100, 0F, 75F, 0.2F))
                    foodDao.insertFood(Food("0","Patate", "Glucides", 0xFFFFF176.toInt(), "", 82F, 100, 0.1F, 18.3F, 1.8F))
                    foodDao.insertFood(Food("0","Pâtes blé complet", "Glucides", 0xFFFFF176.toInt(), "", 360F, 100, 2F, 70F, 13F))
                    foodDao.insertFood(Food("0","Pita", "Glucides", 0xFFFFF176.toInt(), "", 248F, 100, 1F, 50F, 8.2F))
                    foodDao.insertFood(Food("0","Pois chiche", "Glucides", 0xFFFFF176.toInt(), "", 329F, 100, 5.6F, 49.4F, 20.3F))
                    foodDao.insertFood(Food("0","Pop corn", "Glucides", 0xFFFFF176.toInt(), "", 365F, 100, 4.5F, 74F, 9.4F))
                    foodDao.insertFood(Food("0","Quinoa tricolore", "Glucides", 0xFFFFF176.toInt(), "", 361F, 100, 6.9F, 59F, 12F))
                    foodDao.insertFood(Food("0","Riz brun", "Glucides", 0xFFFFF176.toInt(), "", 348F, 100, 3.3F, 70F, 8.2F))
                    foodDao.insertFood(Food("0","Sarrasin", "Glucides", 0xFFFFF176.toInt(), "", 353F, 100, 2.2F, 62.8F, 11.8F))
                    foodDao.insertFood(Food("0","Veggie macaroni pois chiche", "Glucides", 0xFFFFF176.toInt(), "", 363F, 100, 5.7F, 55F, 19F))
                    foodDao.insertFood(Food("0","Veggie penne lentilles corail", "Glucides", 0xFFFFF176.toInt(), "", 335F, 100, 1.7F, 52F, 25F))
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