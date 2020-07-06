package com.gallosalocin.calorie_counter.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.models.User
import com.gallosalocin.calorie_counter.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_bmr.*

class BmrActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmr)

        configToolbar()
        user = User()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        initButton()
    }

    private fun configToolbar() {
        setSupportActionBar(bmr_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bmr_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bmr_toolbar_save -> {
                if (tv_bmr_result.text.isEmpty() || et_daily_calorie_result.text.isNullOrEmpty()) {
                    Toast.makeText(this, getString(R.string.toast_fill_fields), Toast.LENGTH_LONG).show()
                } else {
                    saveUserData()
                    finish()
                    Toast.makeText(this, getString(R.string.profile_saved), Toast.LENGTH_LONG).show()
                }
            }
            R.id.bmr_toolbar_load -> loadUserData()
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun calculateBmr() {
        if (radio_group_gender.checkedRadioButtonId == -1 || age_value.text.isNullOrEmpty() || height_value.text.isNullOrEmpty() || weight_value.text.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.toast_fill_fields), Toast.LENGTH_LONG).show()
        } else {
            user.checkedGender = radio_group_gender.checkedRadioButtonId
            val gender = findViewById<RadioButton>(user.checkedGender)
            user.age = age_value.text.toString().toInt()
            user.height = height_value.text.toString().toInt()
            user.weight = weight_value.text.toString().toInt()

            user.bmrResult =
                (if (gender == gender_male) 66 + (13.7 * user.weight) + (5 * user.height) - (6.8 * user.age) else 655 + (9.6 * user.weight) + (1.8 * user.height) - (4.7 * user.age)).toFloat()

            tv_bmr_result.text = user.bmrResult.toInt().toString()
        }
    }

    private fun calculateDailyCalories() {
        if (radio_group_activity_level.checkedRadioButtonId == -1 || tv_bmr_result.text.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.toast_fill_fields), Toast.LENGTH_LONG).show()
        } else {
            user.checkedActivity = radio_group_activity_level.checkedRadioButtonId

            when (findViewById<RadioButton>(user.checkedActivity)) {
                level1 -> user.dailyCalories = user.bmrResult * 1.2F
                level2 -> user.dailyCalories = user.bmrResult * 1.375F
                level3 -> user.dailyCalories = user.bmrResult * 1.55F
                level4 -> user.dailyCalories = user.bmrResult * 1.725F
                level5 -> user.dailyCalories = user.bmrResult * 1.9F
            }
            et_daily_calorie_result.setText(user.dailyCalories.toInt().toString())
        }
    }

    private fun calculateMacros() {
        if (et_daily_calorie_result.text.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.toast_fill_fields), Toast.LENGTH_LONG).show()
        } else {
            user.fatPercent = fat_percent.text.toString().toInt()
            user.carbPercent = carb_percent.text.toString().toInt()
            user.protPercent = prot_percent.text.toString().toInt()

            user.fatResult = (((user.dailyCalories * user.fatPercent) / 100) / 9).toInt()
            user.carbResult = (((user.dailyCalories * user.carbPercent) / 100) / 4).toInt()
            user.protResult = (((user.dailyCalories * user.protPercent) / 100) / 4).toInt()

            fat_result.text = user.fatResult.toString()
            carb_result.text = user.carbResult.toString()
            prot_result.text = user.protResult.toString()
        }
    }

    private fun saveUserData() {
        user = User(
            1,
            radio_group_gender.checkedRadioButtonId,
            age_value.text.toString().toInt(),
            height_value.text.toString().toInt(),
            weight_value.text.toString().toInt(),
            radio_group_activity_level.checkedRadioButtonId,
            fat_percent.text.toString().toInt(),
            carb_percent.text.toString().toInt(),
            prot_percent.text.toString().toInt(),
            et_daily_calorie_result.text.toString().toFloat(),
            fat_result.text.toString().toInt(),
            carb_result.text.toString().toInt(),
            prot_result.text.toString().toInt(),
            tv_bmr_result.text.toString().toFloat()
        )
        userViewModel.upsertUser(user)
    }

    private fun loadUserData() {
        userViewModel.getUser.observe(this, Observer {
            user = it
            radio_group_gender.check(user.checkedGender)
            age_value.setText(user.age.toString())
            height_value.setText(user.height.toString())
            weight_value.setText(user.weight.toString())
            tv_bmr_result.text = String.format("%.0f", user.bmrResult)
            et_daily_calorie_result.setText(String.format("%.0f", user.dailyCalories))
            radio_group_activity_level.check(user.checkedActivity)
            fat_percent.setText(user.fatPercent.toString())
            carb_percent.setText(user.carbPercent.toString())
            prot_percent.setText(user.protPercent.toString())
            fat_result.text = user.fatResult.toString()
            carb_result.text = user.carbResult.toString()
            prot_result.text = user.protResult.toString()
        })
    }

    private fun initButton() {
        btn_calculate_bmr.setOnClickListener { calculateBmr() }

        btn_calculate_daily_calorie.setOnClickListener {
            calculateBmr()
            calculateDailyCalories()
            calculateMacros()
        }

        btn_calculate_macros.setOnClickListener { calculateMacros() }

        iv_custom_calorie.setOnClickListener {
            user.dailyCalories = et_daily_calorie_result.text.toString().toFloat()
            calculateMacros()
            Toast.makeText(this, getString(R.string.custom_calorie_saved), Toast.LENGTH_SHORT).show()
        }
    }
}