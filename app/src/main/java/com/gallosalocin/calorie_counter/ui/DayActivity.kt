package com.gallosalocin.calorie_counter.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gallosalocin.calorie_counter.R
import kotlinx.android.synthetic.main.activity_day.*
import kotlinx.android.synthetic.main.activity_day.meal_toolbar
import kotlinx.android.synthetic.main.activity_meal.*

class DayActivity : AppCompatActivity() {

    companion object {
        var mealTag = 0
    }

    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val PREF_NAME = "myPref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        setSupportActionBar(meal_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbarNameDay()
        loadData()
        mealChoice()

//        breakfast_cal_current.text = overall_meal_cal_current.toString()
//        breakfast_fat_current.text = overall_meal_fat_current.toString()
//        breakfast_carb_current.text = overall_meal_carb_current.toString()
//        breakfast_prot_current.text = overall_meal_prot_current.toString()

    }


    private fun toolbarNameDay() {
        when (MainActivity.dayTag) {
            1 -> this.title = getString(R.string.monday_cap)
            2 -> this.title = getString(R.string.tuesday_cap)
            3 -> this.title = getString(R.string.wednesday_cap)
            4 -> this.title = getString(R.string.thursday_cap)
            5 -> this.title = getString(R.string.friday_cap)
            6 -> this.title = getString(R.string.saturday_cap)
            7 -> this.title = getString(R.string.sunday_cap)
        }
    }

    private fun loadData() {
        sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

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