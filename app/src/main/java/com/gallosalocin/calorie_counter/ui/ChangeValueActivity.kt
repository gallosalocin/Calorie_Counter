package com.gallosalocin.calorie_counter.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.viewmodel.FoodViewModel
import kotlinx.android.synthetic.main.activity_change_value.*
import kotlinx.android.synthetic.main.activity_details.*

class ChangeValueActivity : AppCompatActivity() {

    private lateinit var foodViewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_value)

        configToolbar()
        onGetExtras()
        configEnterButtonSoftKeyboard()
        isEditableFood()

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

    }

    private fun isEditableFood() {
        if (SearchActivity.isEditableFood) {
            onGetExtras()
            SearchActivity.isEditableFood = false
        }
    }

    private fun configToolbar() {
        setSupportActionBar(change_value_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.change_value_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_value_toolbar_save -> {
                confirmInput()
            }
            android.R.id.home -> {
                onBackPressed()
                finish()
            }
        }
        return true
    }

    private fun onGetExtras() {
        val food = intent.getSerializableExtra(SearchActivity.EXTRA_FOOD) as Food

        tv_change_item_title.text = food.name
        et_change_value.setText(food.weight.toString())
    }

    // Press enter to save
    private fun configEnterButtonSoftKeyboard() {
        et_change_value.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                confirmInput()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    private fun confirmInput() {
        if (!validateWeight()) {
            return
        }
        saveCreatedFood()
        finish()
        Toast.makeText(this, getString(R.string.weight_replace), Toast.LENGTH_SHORT).show()
    }

    private fun saveCreatedFood() {

        foodViewModel.insertFood(
            Food(
                et_details_name.text.toString(),
                spinner_category.selectedItem.toString(),
                spinner_category.tag as Int,
                details_note.text.toString(),
                et_details_calorie.text.toString().toInt(),
                et_change_value.text.toString().toInt(),
                et_details_fat.text.toString().toFloat(),
                et_details_carb.text.toString().toFloat(),
                et_details_prot.text.toString().toFloat()
            )
        )
    }

    private fun validateWeight(): Boolean {
        val weight = et_change_value.text.toString().trim()

        return if (weight.isEmpty()) {
            change_value.error = getString(R.string.error_add_value)
            false
        } else {
            change_value.error = null
            true
        }
    }
}