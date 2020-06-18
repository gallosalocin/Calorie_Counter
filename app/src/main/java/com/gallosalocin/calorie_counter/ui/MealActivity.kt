package com.gallosalocin.calorie_counter.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.adapters.FoodAdapter
import com.gallosalocin.calorie_counter.models.Food
import kotlinx.android.synthetic.main.activity_day.meal_toolbar
import kotlinx.android.synthetic.main.activity_meal.*

class MealActivity : AppCompatActivity() {

    private lateinit var foodAdapter: FoodAdapter

    companion object{
         var foodMealsList: MutableList<Food> = ArrayList()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        configToolbar()
        toolbarMealName()
        setupFabAddFood()

//        foodMealsList = ArrayList()


//        chooseCorrectList()
        if (MainActivity.dayTag == 1 && DayActivity.mealTag == 1) {
            foodMealsList = ArrayList()
            foodMealsList.add(Food("Carottes", "Legume", 0xFF48B34D.toInt(),"", 34, 100, 0.4F, 7.7F, 0.5F))
        }
        if (MainActivity.dayTag == 2 && DayActivity.mealTag == 1) {
            foodMealsList.add(Food("Blanc de poulet", "Proteine", 0xFFE57373.toInt(),"", 162, 150, 2F, 0.7F, 35.3F))
        }
        if (MainActivity.dayTag != 1 && DayActivity.mealTag != 1 || MainActivity.dayTag != 2 && DayActivity.mealTag != 1){
            foodMealsList.add(Food("Glace", getString(R.string.carbohydrate), 0xFFFFF176.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        }


        setupRecycleView()

    }

    private fun configToolbar() {
        setSupportActionBar(meal_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupFabAddFood(){
        fab_meal_food_add.setOnClickListener {
            Intent(this, SearchActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun setupRecycleView() {
        foodAdapter = FoodAdapter()
        rv_meal.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@MealActivity)
            foodAdapter.submitList(foodMealsList)
        }

        foodAdapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {

            override fun setOnClickListener(position: Int) {
                Toast.makeText(applicationContext, foodMealsList[position].name, Toast.LENGTH_SHORT).show()
            }

            override fun setOnLongClickListener(position: Int) {
                Toast.makeText(applicationContext, foodMealsList[position].name, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun toolbarMealName() {
        when (DayActivity.mealTag) {
            1 -> this.title = getString(R.string.breakfast_cap)
            2 -> this.title = getString(R.string.lunch_cap)
            3 -> this.title = getString(R.string.dinner_cap)
            4 -> this.title = getString(R.string.snack_cap)
        }
    }

    private fun chooseCorrectList(){
        if (MainActivity.dayTag == 1 && DayActivity.mealTag == 1 ||
            MainActivity.dayTag == 1 && DayActivity.mealTag == 2 ||
            MainActivity.dayTag == 1 && DayActivity.mealTag == 3 ||
            MainActivity.dayTag == 1 && DayActivity.mealTag == 4 ||
            MainActivity.dayTag == 2 && DayActivity.mealTag == 1 ||
            MainActivity.dayTag == 2 && DayActivity.mealTag == 2 ||
            MainActivity.dayTag == 2 && DayActivity.mealTag == 3 ||
            MainActivity.dayTag == 2 && DayActivity.mealTag == 4 ||
            MainActivity.dayTag == 3 && DayActivity.mealTag == 1 ||
            MainActivity.dayTag == 3 && DayActivity.mealTag == 2 ||
            MainActivity.dayTag == 3 && DayActivity.mealTag == 3 ||
            MainActivity.dayTag == 3 && DayActivity.mealTag == 4 ||
            MainActivity.dayTag == 4 && DayActivity.mealTag == 1 ||
            MainActivity.dayTag == 4 && DayActivity.mealTag == 2 ||
            MainActivity.dayTag == 4 && DayActivity.mealTag == 3 ||
            MainActivity.dayTag == 4 && DayActivity.mealTag == 4 ||
            MainActivity.dayTag == 5 && DayActivity.mealTag == 1 ||
            MainActivity.dayTag == 5 && DayActivity.mealTag == 2 ||
            MainActivity.dayTag == 5 && DayActivity.mealTag == 3 ||
            MainActivity.dayTag == 5 && DayActivity.mealTag == 4 ||
            MainActivity.dayTag == 6 && DayActivity.mealTag == 1 ||
            MainActivity.dayTag == 6 && DayActivity.mealTag == 2 ||
            MainActivity.dayTag == 6 && DayActivity.mealTag == 3 ||
            MainActivity.dayTag == 6 && DayActivity.mealTag == 4 ||
            MainActivity.dayTag == 7 && DayActivity.mealTag == 1 ||
            MainActivity.dayTag == 7 && DayActivity.mealTag == 2 ||
            MainActivity.dayTag == 7 && DayActivity.mealTag == 3 ||
            MainActivity.dayTag == 7 && DayActivity.mealTag == 4 )
            setupRecycleView()
    }

    private fun addFood() {
        foodMealsList.add(Food("Blanc de poulet", "Proteine", 0xFFE57373.toInt(),"", 162, 150, 2F, 0.7F, 35.3F))
        foodMealsList.add(Food("Coco Plats", "Legume", 0xFF48B34D.toInt(),"", 75, 250, 0.5F, 9.1F, 4.6F))
        foodMealsList.add(Food("Carottes", "Legume", 0xFF48B34D.toInt(),"", 34, 100, 0.4F, 7.7F, 0.5F))
        foodMealsList.add(Food("Huile d'Olive", "Huile", 0xFFC1A36E.toInt(),"", 90, 10, 10F, 0F, 0F))
    }
}