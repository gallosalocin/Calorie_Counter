package com.gallosalocin.calorie_counter.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gallosalocin.calorie_counter.R
import kotlinx.android.synthetic.main.activity_bmr.*

class BmrActivity : AppCompatActivity() {

    private var resultBmr = 0F
    private var checkedGender = 0
    private var age = 0
    private var height = 0
    private var weight = 0
    private var checkedActivity = 0
    private var fatPercent = 0
    private var carbPercent = 0
    private var protPercent = 0
    private var dailyCalories = 0F
    private var fatResult = 0
    private var carbResult = 0
    private var protResult = 0


    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val PREF_NAME = "myPref"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmr)

        setSupportActionBar(meal_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        loadData()

        btn_calculate_bmr.setOnClickListener { calculateBmr() }
        btn_calculate_daily_calorie.setOnClickListener {
            calculateBmr()
            calculateDailyCalories()
            calculateMacros()
        }
        btn_calculate_macros.setOnClickListener { calculateMacros() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bmr_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bmr_toolbar_save -> {
                calculateBmr()
                calculateDailyCalories()
                calculateMacros()
                savedData()
                finish()
                Toast.makeText(this, getString(R.string.profile_saved), Toast.LENGTH_LONG).show()
            }
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun calculateBmr() {

        checkedGender = radio_group_gender.checkedRadioButtonId
        val gender = findViewById<RadioButton>(checkedGender)
        age = age_value.text.toString().toInt()
        height = height_value.text.toString().toInt()
        weight = weight_value.text.toString().toInt()

        resultBmr =
            (if (gender == gender_male) 66 + (13.7 * weight) + (5 * height) - (6.8 * age) else 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age)).toFloat()

        tv_bmr_result.text = resultBmr.toInt().toString()
    }

    private fun calculateDailyCalories() {
        checkedActivity = radio_group_activity_level.checkedRadioButtonId

        when (findViewById<RadioButton>(checkedActivity)) {
            level1 -> dailyCalories = resultBmr * 1.2F
            level2 -> dailyCalories = resultBmr * 1.375F
            level3 -> dailyCalories = resultBmr * 1.55F
            level4 -> dailyCalories = resultBmr * 1.725F
            level5 -> dailyCalories = resultBmr * 1.9F
        }
        tv_daily_calorie_result.text = dailyCalories.toInt().toString()
    }

    private fun calculateMacros() {

        fatPercent = fat_percent.text.toString().toInt()
        carbPercent = carb_percent.text.toString().toInt()
        protPercent = prot_percent.text.toString().toInt()

        fatResult = (((dailyCalories * fatPercent) / 100) / 9).toInt()
        carbResult = (((dailyCalories * carbPercent) / 100) / 4).toInt()
        protResult = (((dailyCalories * protPercent) / 100) / 4).toInt()

        fat_result.text = fatResult.toString()
        carb_result.text = carbResult.toString()
        prot_result.text = protResult.toString()

    }

    private fun savedData() {
        sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        editor.apply {
            putInt("checkedGender", checkedGender)
            putInt("age", age)
            putInt("height", height)
            putInt("weight", weight)
            putFloat("result", resultBmr)
            putInt("checkedActivity", checkedActivity)
            putFloat("dailyCalories", dailyCalories)
            putInt("fatPercent", fatPercent)
            putInt("carbPercent", carbPercent)
            putInt("protPercent", protPercent)
            putInt("fatResult", fatResult)
            putInt("carbResult", carbResult)
            putInt("protResult", protResult)
            apply()
        }
    }

    private fun loadData() {
        sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val savedCheckedGender = sharedPref.getInt("checkedGender", 0)
        val savedAge = sharedPref.getInt("age", 0)
        val savedHeight = sharedPref.getInt("height", 0)
        val savedWeight = sharedPref.getInt("weight", 0)
        val savedResult = sharedPref.getFloat("result", 0F)
        val savedCheckedActivity = sharedPref.getInt("checkedActivity", 0)
        val savedDailyCalorie = sharedPref.getFloat("dailyCalories", 0F)
        val savedFatPercent = sharedPref.getInt("fatPercent", 30)
        val savedCarbPercent = sharedPref.getInt("carbPercent", 40)
        val savedProtPercent = sharedPref.getInt("protPercent", 30)
        val savedFatResult = sharedPref.getInt("fatResult", 0)
        val savedCarbResult = sharedPref.getInt("carbResult", 0)
        val savedProtResult = sharedPref.getInt("protResult", 0)

        radio_group_gender.check(savedCheckedGender)
        age_value.setText(savedAge.toString())
        height_value.setText(savedHeight.toString())
        weight_value.setText(savedWeight.toString())
        tv_bmr_result.text = savedResult.toInt().toString()
        radio_group_activity_level.check(savedCheckedActivity)
        tv_daily_calorie_result.text = savedDailyCalorie.toInt().toString()
        fat_percent.setText(savedFatPercent.toString())
        carb_percent.setText(savedCarbPercent.toString())
        prot_percent.setText(savedProtPercent.toString())
        fat_result.text = savedFatResult.toString()
        carb_result.text = savedCarbResult.toString()
        prot_result.text = savedProtResult.toString()
    }
}