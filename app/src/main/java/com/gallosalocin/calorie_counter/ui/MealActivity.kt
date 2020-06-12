package com.gallosalocin.calorie_counter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.adapters.FoodAdapter
import com.gallosalocin.calorie_counter.models.Food
import kotlinx.android.synthetic.main.activity_day.meal_toolbar
import kotlinx.android.synthetic.main.activity_meal.*

class MealActivity : AppCompatActivity() {

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodList: MutableList<Food>

    companion object {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        setSupportActionBar(meal_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbarMealName()


        foodList = ArrayList()
        addFood()

        setupRecycleView()

        if (MainActivity.dayTag == 1 && DayActivity.mealTag == 1) {
            foodAdapter.submitList(foodList)
        }
    }

    private fun addFood() {
        foodList.add(Food("Blanc de poulet", "Proteine", "", 162, 150, 2F, 0.7F, 35.3F))
        foodList.add(Food("Coco Plats", "Legume", "", 75, 250, 0.5F, 9.1F, 4.6F))
        foodList.add(Food("Carottes", "Legume", "", 34, 100, 0.4F, 7.7F, 0.5F))
        foodList.add(Food("Huile d'Olive", "Huile", "", 90, 10, 10F, 0F, 0F))
    }

    private fun setupRecycleView() {
        foodAdapter = FoodAdapter()
        recycler_view.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@MealActivity)

        }
    }

    private fun toolbarMealName() {
        when (DayActivity.mealTag) {
            1 -> this.title = "Petit Déjeuner"
            2 -> this.title = "Snack Matin"
            3 -> this.title = "Déjeuner"
            4 -> this.title = "Snack Après-Midi"
            5 -> this.title = "Dîner"
            6 -> this.title = "Snack Soirée"
        }
    }
}