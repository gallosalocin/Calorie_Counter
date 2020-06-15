package com.gallosalocin.calorie_counter.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.adapters.FoodAdapter
import com.gallosalocin.calorie_counter.models.Food
import kotlinx.android.synthetic.main.activity_day.meal_toolbar
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_meal.*

class MealActivity : AppCompatActivity() {

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var mondayBreakfastList: MutableList<Food>
    private lateinit var mondayLunchList: MutableList<Food>
    private lateinit var mondayDinnerList: MutableList<Food>
    private lateinit var mondaySnackList: MutableList<Food>
    private lateinit var tuesdayBreakfastList: MutableList<Food>
    private lateinit var tuesdayLunchList: MutableList<Food>
    private lateinit var tuesdayDinnerList: MutableList<Food>
    private lateinit var tuesdaySnackList: MutableList<Food>
    private lateinit var wednesdayBreakfastList: MutableList<Food>
    private lateinit var wednesdayLunchList: MutableList<Food>
    private lateinit var wednesdayDinnerList: MutableList<Food>
    private lateinit var wednesdaySnackList: MutableList<Food>
    private lateinit var thursdayBreakfastList: MutableList<Food>
    private lateinit var thursdayLunchList: MutableList<Food>
    private lateinit var thursdayDinnerList: MutableList<Food>
    private lateinit var thursdaySnackList: MutableList<Food>
    private lateinit var fridayBreakfastList: MutableList<Food>
    private lateinit var fridayLunchList: MutableList<Food>
    private lateinit var fridayDinnerList: MutableList<Food>
    private lateinit var fridaySnackList: MutableList<Food>
    private lateinit var saturdayBreakfastList: MutableList<Food>
    private lateinit var saturdayLunchList: MutableList<Food>
    private lateinit var saturdayDinnerList: MutableList<Food>
    private lateinit var saturdaySnackList: MutableList<Food>
    private lateinit var sundayBreakfastList: MutableList<Food>
    private lateinit var sundayLunchList: MutableList<Food>
    private lateinit var sundayDinnerList: MutableList<Food>
    private lateinit var sundaySnackList: MutableList<Food>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        configToolbar()
        toolbarMealName()

        setupFabAddFood()
        setupAllLists()
        addFood()
        setupRecycleView()
        chooseCorrectList()
    }

    private fun configToolbar() {
        setSupportActionBar(meal_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupFabAddFood(){
        fab_meal_food_add.setOnClickListener {
            Intent(this, SearchActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun addFood() {
        mondayBreakfastList.add(Food("Blanc de poulet", "Proteine", 0xFFE57373.toInt(),"", 162, 150, 2F, 0.7F, 35.3F))
        mondayBreakfastList.add(Food("Coco Plats", "Legume", 0xFF48B34D.toInt(),"", 75, 250, 0.5F, 9.1F, 4.6F))
        mondayBreakfastList.add(Food("Carottes", "Legume", 0xFF48B34D.toInt(),"", 34, 100, 0.4F, 7.7F, 0.5F))
        mondayBreakfastList.add(Food("Huile d'Olive", "Huile", 0xFFC1A36E.toInt(),"", 90, 10, 10F, 0F, 0F))
    }

    private fun setupRecycleView() {
        foodAdapter = FoodAdapter()
        rv_meal.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@MealActivity)
        }
    }

    private fun toolbarMealName() {
        when (DayActivity.mealTag) {
            1 -> this.title = getString(R.string.breakfast_cap)
            2 -> this.title = getString(R.string.lunch_cap)
            3 -> this.title = getString(R.string.dinner_cap)
            4 -> this.title = getString(R.string.snack_cap)
        }
    }

    private fun setupAllLists() {
        mondayBreakfastList = ArrayList()
        mondayLunchList = ArrayList()
        mondayDinnerList = ArrayList()
        mondaySnackList = ArrayList()
        tuesdayBreakfastList = ArrayList()
        tuesdayLunchList = ArrayList()
        tuesdayDinnerList = ArrayList()
        tuesdaySnackList = ArrayList()
        wednesdayBreakfastList = ArrayList()
        wednesdayLunchList = ArrayList()
        wednesdayDinnerList = ArrayList()
        wednesdaySnackList = ArrayList()
        thursdayBreakfastList = ArrayList()
        thursdayLunchList = ArrayList()
        thursdayDinnerList = ArrayList()
        thursdaySnackList = ArrayList()
        fridayBreakfastList = ArrayList()
        fridayLunchList = ArrayList()
        fridayDinnerList = ArrayList()
        fridaySnackList = ArrayList()
        saturdayBreakfastList = ArrayList()
        saturdayLunchList = ArrayList()
        saturdayDinnerList = ArrayList()
        saturdaySnackList = ArrayList()
        sundayBreakfastList = ArrayList()
        sundayLunchList = ArrayList()
        sundayDinnerList = ArrayList()
        sundaySnackList = ArrayList()
    }

    private fun chooseCorrectList(){
        // Monday
        if (MainActivity.dayTag == 1 && DayActivity.mealTag == 1) foodAdapter.submitList(mondayBreakfastList)
        if (MainActivity.dayTag == 1 && DayActivity.mealTag == 2) foodAdapter.submitList(mondayLunchList)
        if (MainActivity.dayTag == 1 && DayActivity.mealTag == 3) foodAdapter.submitList(mondayDinnerList)
        if (MainActivity.dayTag == 1 && DayActivity.mealTag == 4) foodAdapter.submitList(mondaySnackList)
        // Tuesday
        if (MainActivity.dayTag == 2 && DayActivity.mealTag == 1) foodAdapter.submitList(tuesdayBreakfastList)
        if (MainActivity.dayTag == 2 && DayActivity.mealTag == 2) foodAdapter.submitList(tuesdayLunchList)
        if (MainActivity.dayTag == 2 && DayActivity.mealTag == 3) foodAdapter.submitList(tuesdayDinnerList)
        if (MainActivity.dayTag == 2 && DayActivity.mealTag == 4) foodAdapter.submitList(tuesdaySnackList)
        // Wednesday
        if (MainActivity.dayTag == 3 && DayActivity.mealTag == 1) foodAdapter.submitList(wednesdayBreakfastList)
        if (MainActivity.dayTag == 3 && DayActivity.mealTag == 2) foodAdapter.submitList(wednesdayLunchList)
        if (MainActivity.dayTag == 3 && DayActivity.mealTag == 3) foodAdapter.submitList(wednesdayDinnerList)
        if (MainActivity.dayTag == 3 && DayActivity.mealTag == 4) foodAdapter.submitList(wednesdaySnackList)
        // Thursday
        if (MainActivity.dayTag == 4 && DayActivity.mealTag == 1) foodAdapter.submitList(thursdayBreakfastList)
        if (MainActivity.dayTag == 4 && DayActivity.mealTag == 2) foodAdapter.submitList(thursdayLunchList)
        if (MainActivity.dayTag == 4 && DayActivity.mealTag == 3) foodAdapter.submitList(thursdayDinnerList)
        if (MainActivity.dayTag == 4 && DayActivity.mealTag == 4) foodAdapter.submitList(thursdaySnackList)
        // Friday
        if (MainActivity.dayTag == 5 && DayActivity.mealTag == 1) foodAdapter.submitList(fridayBreakfastList)
        if (MainActivity.dayTag == 5 && DayActivity.mealTag == 2) foodAdapter.submitList(fridayLunchList)
        if (MainActivity.dayTag == 5 && DayActivity.mealTag == 3) foodAdapter.submitList(fridayDinnerList)
        if (MainActivity.dayTag == 5 && DayActivity.mealTag == 4) foodAdapter.submitList(fridaySnackList)
        // Saturday
        if (MainActivity.dayTag == 6 && DayActivity.mealTag == 1) foodAdapter.submitList(saturdayBreakfastList)
        if (MainActivity.dayTag == 6 && DayActivity.mealTag == 2) foodAdapter.submitList(saturdayLunchList)
        if (MainActivity.dayTag == 6 && DayActivity.mealTag == 3) foodAdapter.submitList(saturdayDinnerList)
        if (MainActivity.dayTag == 6 && DayActivity.mealTag == 4) foodAdapter.submitList(saturdaySnackList)
        // Sunday
        if (MainActivity.dayTag == 7 && DayActivity.mealTag == 1) foodAdapter.submitList(sundayBreakfastList)
        if (MainActivity.dayTag == 7 && DayActivity.mealTag == 2) foodAdapter.submitList(sundayLunchList)
        if (MainActivity.dayTag == 7 && DayActivity.mealTag == 3) foodAdapter.submitList(sundayDinnerList)
        if (MainActivity.dayTag == 7 && DayActivity.mealTag == 4) foodAdapter.submitList(sundaySnackList)
    }
}