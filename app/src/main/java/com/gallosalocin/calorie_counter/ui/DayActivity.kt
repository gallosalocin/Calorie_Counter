package com.gallosalocin.calorie_counter.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.viewmodel.FoodViewModel
import kotlinx.android.synthetic.main.activity_day.*

class DayActivity : AppCompatActivity() {

    private lateinit var foodViewModel: FoodViewModel
    private lateinit var allFoodByBreakfast: List<Food>
    private lateinit var allFoodByLunch: List<Food>
    private lateinit var allFoodByDinner: List<Food>
    private lateinit var allFoodBySnack: List<Food>
    private lateinit var allFoodMacrosTotal: List<Food>

    companion object {
        var mealTag = 0
    }

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        setSupportActionBar(bmr_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbarNameDay(MainActivity.dayTag)
        loadData()
        mealChoice()

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        calculateMacrosBreakfast()
        calculateMacrosLunch()
        calculateMacrosDinner()
        calculateMacrosSnack()
        calculateMacrosTotal()

    }

    private fun calculateMacrosTotal() {
        foodViewModel.allFoodsMacrosTotal.observe(this, androidx.lifecycle.Observer { foods ->
            allFoodMacrosTotal = foods

            day_cal_total.text = String.format("%.0f", allFoodMacrosTotal.sumByDouble { it.calorie.toDouble() })
            day_fat_total.text = String.format("%.1f", allFoodMacrosTotal.sumByDouble { it.fat.toDouble() })
            day_carb_total.text = String.format("%.1f", allFoodMacrosTotal.sumByDouble { it.carb.toDouble() })
            day_prot_total.text = String.format("%.1f", allFoodMacrosTotal.sumByDouble { it.prot.toDouble() })
        })
    }

    private fun calculateMacrosBreakfast() {
        foodViewModel.allFoodsByBreakfast.observe(this, androidx.lifecycle.Observer { foods ->
            allFoodByBreakfast = foods

            breakfast_day_cal.text = String.format("%.0f", allFoodByBreakfast.sumByDouble { it.calorie.toDouble() })
            breakfast_day_fat.text = String.format("%.1f", allFoodByBreakfast.sumByDouble { it.fat.toDouble() })
            breakfast_day_carb.text = String.format("%.1f", allFoodByBreakfast.sumByDouble { it.carb.toDouble() })
            breakfast_day_prot.text = String.format("%.1f", allFoodByBreakfast.sumByDouble { it.prot.toDouble() })
        })
    }

    private fun calculateMacrosLunch() {
        foodViewModel.allFoodsByLunch.observe(this, androidx.lifecycle.Observer { foods ->
            allFoodByLunch = foods

            lunch_day_cal.text = String.format("%.0f", allFoodByLunch.sumByDouble { it.calorie.toDouble() })
            lunch_day_fat.text = String.format("%.1f", allFoodByLunch.sumByDouble { it.fat.toDouble() })
            lunch_day_carb.text = String.format("%.1f", allFoodByLunch.sumByDouble { it.carb.toDouble() })
            lunch_day_prot.text = String.format("%.1f", allFoodByLunch.sumByDouble { it.prot.toDouble() })
        })
    }

    private fun calculateMacrosDinner() {
        foodViewModel.allFoodsByDinner.observe(this, androidx.lifecycle.Observer { foods ->
            allFoodByDinner = foods
            dinner_day_cal.text = String.format("%.0f", allFoodByDinner.sumByDouble { it.calorie.toDouble() })
            dinner_day_fat.text = String.format("%.1f", allFoodByDinner.sumByDouble { it.fat.toDouble() })
            dinner_day_carb.text = String.format("%.1f", allFoodByDinner.sumByDouble { it.carb.toDouble() })
            dinner_day_prot.text = String.format("%.1f", allFoodByDinner.sumByDouble { it.prot.toDouble() })
        })
    }

    private fun calculateMacrosSnack() {
        foodViewModel.allFoodsBySnack.observe(this, androidx.lifecycle.Observer { foods ->
            allFoodBySnack = foods
            snack_day_cal.text = String.format("%.0f", allFoodBySnack.sumByDouble { it.calorie.toDouble() })
            snack_day_fat.text = String.format("%.1f", allFoodBySnack.sumByDouble { it.fat.toDouble() })
            snack_day_carb.text = String.format("%.1f", allFoodBySnack.sumByDouble { it.carb.toDouble() })
            snack_day_prot.text = String.format("%.1f", allFoodBySnack.sumByDouble { it.prot.toDouble() })
        })
    }

    private fun toolbarNameDay(dayTag: Int) {
        val dayTagArray = arrayOf(
            getString(R.string.monday_cap), getString(R.string.tuesday_cap), getString(R.string.wednesday_cap), getString(R.string.thursday_cap),
            getString(R.string.friday_cap), getString(R.string.saturday_cap), getString(R.string.sunday_cap)
        )

        this.title = dayTagArray[dayTag - 1]
    }

    private fun loadData() {
        sharedPref = getSharedPreferences(BmrActivity.PREF_BMR, Context.MODE_PRIVATE)

        val savedDailyCalorie = sharedPref.getFloat("dailyCalories", 0F)
        val savedFatResult = sharedPref.getInt("fatResult", 0)
        val savedCarbResult = sharedPref.getInt("carbResult", 0)
        val savedProtResult = sharedPref.getInt("protResult", 0)

        overall_day_cal_total.text = savedDailyCalorie.toInt().toString()
        overall_day_fat_total.text = savedFatResult.toString()
        overall_day_carb_total.text = savedCarbResult.toString()
        overall_day_prot_total.text = savedProtResult.toString()
    }

    private fun mealChoice() {
        cv_breakfast.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 1
                startActivity(it)
            }
        }
        cv_lunch.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 2
                startActivity(it)
            }
        }
        cv_dinner.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 3
                startActivity(it)
            }
        }
        cv_snack.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 4
                startActivity(it)
            }
        }
    }
}