package com.gallosalocin.calorie_counter.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gallosalocin.calorie_counter.R
import kotlinx.android.synthetic.main.activity_day.*

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

    }


    private fun toolbarNameDay() {
        when (MainActivity.dayTag) {
            1 -> this.title = "Lundi"
            2 -> this.title = "Mardi"
            3 -> this.title = "Mercredi"
            4 -> this.title = "Jeudi"
            5 -> this.title = "Vendredi"
            6 -> this.title = "Samedi"
            7 -> this.title = "Dimanche"
        }
    }

    private fun loadData() {
        sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val savedDailyCalorie = sharedPref.getFloat("dailyCalories", 0F)
        val savedFatResult = sharedPref.getInt("fatResult", 0)
        val savedCarbResult = sharedPref.getInt("carbResult", 0)
        val savedProtResult = sharedPref.getInt("protResult", 0)

        overall_cal_total.text = savedDailyCalorie.toInt().toString()
        overall_fat_total.text = savedFatResult.toString()
        overall_carb_total.text = savedCarbResult.toString()
        overall_prot_total.text = savedProtResult.toString()
    }

    private fun mealChoice() {
        cv_breakfast.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 1
                Toast.makeText(this, "The daytag is : ${MainActivity.dayTag} and the mealtag is : $mealTag", Toast.LENGTH_SHORT).show()
                startActivity(it)
            }
        }
        cv_breakfast_snack.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 2
                Toast.makeText(this, "The daytag is : ${MainActivity.dayTag} and the mealtag is : $mealTag", Toast.LENGTH_SHORT).show()
                startActivity(it)
            }
        }
        cv_lunch.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 3
                Toast.makeText(this, "The daytag is : ${MainActivity.dayTag} and the mealtag is : $mealTag", Toast.LENGTH_SHORT).show()
                startActivity(it)
            }
        }
        cv_lunch_snack.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 4
                Toast.makeText(this, "The daytag is : ${MainActivity.dayTag} and the mealtag is : $mealTag", Toast.LENGTH_SHORT).show()
                startActivity(it)
            }
        }
        cv_dinner.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 5
                Toast.makeText(this, "The daytag is : ${MainActivity.dayTag} and the mealtag is : $mealTag", Toast.LENGTH_SHORT).show()
                startActivity(it)
            }
        }
        cv_dinner_snack.setOnClickListener {
            Intent(this, MealActivity::class.java).also {
                mealTag = 6
                Toast.makeText(this, "The daytag is : ${MainActivity.dayTag} and the mealtag is : $mealTag", Toast.LENGTH_SHORT).show()
                startActivity(it)
            }
        }
    }
}