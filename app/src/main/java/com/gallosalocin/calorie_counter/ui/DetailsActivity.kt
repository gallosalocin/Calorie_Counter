package com.gallosalocin.calorie_counter.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.utils.Category
import com.gallosalocin.calorie_counter.viewmodel.FoodViewModel
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val categoryList: MutableList<Category> = ArrayList()
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var food: Food

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        configToolbar()
        configSpinner()

        isEditableFood()
        isInvisible()
        configEnterButtonSoftKeyboard()

        et_details_weight.requestFocus()

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

    }


    private fun configToolbar() {
        setSupportActionBar(details_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.details_toolbar_save -> {
                confirmInput()
            }
            android.R.id.home -> {
                onBackPressed()
                finish()
            }
        }
        return true
    }

    private fun isEditableFood() {
        if (SearchActivity.isEditableFood) {
            onGetExtrasFromSearch()
        }
        if (MealActivity.isEditableFood) {
            onGetExtrasFromMeal()
        }
    }

    private fun onGetExtrasFromSearch() {
        food = intent.getSerializableExtra(SearchActivity.EXTRA_FOOD) as Food
        var position = 0

        for (item in categoryList) {
            if (item.name == food.category) {
                position = categoryList.indexOf(item)
            }
        }

        et_details_name.setText(food.name)
        spinner_category.setSelection(position)
        et_details_calorie.setText(((100 * food.calorie) / food.weight).toString())
        et_details_weight.setText(((100 * food.weight) / food.weight).toString())
        et_details_fat.setText(((100 * food.fat) / food.weight).toString())
        et_details_carb.setText(((100 * food.carb) / food.weight).toString())
        et_details_prot.setText(((100 * food.prot) / food.weight).toString())
        details_note.setText(food.note)
    }

    private fun onGetExtrasFromMeal() {
        food = intent.getSerializableExtra(MealActivity.EXTRA_FOOD) as Food
        var position = 0

        for (item in categoryList) {
            if (item.name == food.category) {
                position = categoryList.indexOf(item)
            }
        }

        et_details_name.setText(food.name)
        spinner_category.setSelection(position)
        et_details_calorie.setText(((100 * food.calorie) / food.weight).toString())
        et_details_weight.setText(((100 * food.weight) / food.weight).toString())
        et_details_fat.setText(((100 * food.fat) / food.weight).toString())
        et_details_carb.setText(((100 * food.carb) / food.weight).toString())
        et_details_prot.setText(((100 * food.prot) / food.weight).toString())
        details_note.setText(food.note)
    }

    private fun saveOrUpdateFood() {

        val saveOrUpdateFood = Food(
            "0",
            et_details_name.text.toString(),
            spinner_category.selectedItem.toString(),
            spinner_category.tag as Int,
            details_note.text.toString(),
            et_details_calorie.text.toString().toFloat(),
            100,
            et_details_fat.text.toString().toFloat(),
            et_details_carb.text.toString().toFloat(),
            et_details_prot.text.toString().toFloat()
        )

        if (SearchActivity.isEditableFood || MealActivity.isEditableFood) {
            saveOrUpdateFood.id = food.id
            saveOrUpdateFood.dayMealId = food.dayMealId
            saveOrUpdateFood.weight = et_details_weight.text.toString().toInt()
            saveOrUpdateFood.calorie = ((saveOrUpdateFood.weight.toFloat() / 100) * ((100 * food.calorie) / food.weight))
            saveOrUpdateFood.fat = (saveOrUpdateFood.weight.toFloat() / 100) * ((100 * food.fat) / food.weight)
            saveOrUpdateFood.carb = (saveOrUpdateFood.weight.toFloat() / 100) * ((100 * food.carb) / food.weight)
            saveOrUpdateFood.prot = (saveOrUpdateFood.weight.toFloat() / 100) * ((100 * food.prot) / food.weight)
            foodViewModel.updateFood(saveOrUpdateFood)
            SearchActivity.isEditableFood = false
            MealActivity.isEditableFood = false
            Toast.makeText(this, "Aliment actualisé", Toast.LENGTH_SHORT).show()
        } else {
            foodViewModel.insertFood(saveOrUpdateFood)
            Toast.makeText(this, "Aliment ajouté", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configSpinner() {
        categoryList.add(Category(getString(R.string.choose_category), 0xFFFFFFFF.toInt()))
        categoryList.add(Category(getString(R.string.proteins), 0xFFE57373.toInt()))
        categoryList.add(Category(getString(R.string.carbohydrate), 0xFFFFF176.toInt()))
        categoryList.add(Category(getString(R.string.veggies), 0xFF48B34D.toInt()))
        categoryList.add(Category(getString(R.string.fruits), 0xFF9575CD.toInt()))
        categoryList.add(Category(getString(R.string.healthy_fats), 0xFF4DD0E1.toInt()))
        categoryList.add(Category(getString(R.string.oils), 0xFFC1A36E.toInt()))

        val adapter = ArrayAdapter(this, R.layout.spinner_custom_layout, categoryList)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner_category.adapter = adapter

        spinner_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val category = adapterView?.getItemAtPosition(position) as Category
                spinner_category.tag = category.color
            }
        }
    }

    // Make visible or invisible views
    private fun View.toggleVisibility() {
        visibility = if (visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    private fun isInvisible() {
        if (SearchActivity.isInvisible || MealActivity.isInvisible) {
            spinner_category.toggleVisibility()
            tv_nutrition_facts_text.toggleVisibility()
            et_details_calorie.toggleVisibility()
            details_calorie.toggleVisibility()
            et_details_fat.toggleVisibility()
            details_fat.toggleVisibility()
            et_details_carb.toggleVisibility()
            details_carb.toggleVisibility()
            et_details_prot.toggleVisibility()
            details_prot.toggleVisibility()

            et_details_weight.toggleVisibility()
            details_weight.toggleVisibility()

            SearchActivity.isInvisible = false
            MealActivity.isInvisible = false
        }
    }

    // Press enter to save
    private fun configEnterButtonSoftKeyboard() {
        et_details_weight.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                confirmInput()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    // Validate Input Methods
    private fun confirmInput() {
        if (!validateName() || !validateCategory() || !validateCalorie() || !validateFat() || !validateCarb() || !validateProt()) {
            return
        }
        saveOrUpdateFood()
        onBackPressed()
    }

    private fun validateName(): Boolean {
        val name = et_details_name.text.toString().trim()

        return if (name.isEmpty()) {
            details_name.error = getString(R.string.error_add_value)
            false
        } else {
            details_name.error = null
            true
        }
    }

    private fun validateCategory(): Boolean {
        val category = spinner_category.selectedItem.toString().trim()
        val errorText: TextView = spinner_category.selectedView as TextView

        return if (category == getString(R.string.choose_category)) {
            errorText.error = getString(R.string.error_add_value)
            false
        } else {
            errorText.error = null
            true
        }
    }

    private fun validateCalorie(): Boolean {
        val calorie = et_details_calorie.text.toString().trim()

        return if (calorie.isEmpty()) {
            details_calorie.error = getString(R.string.error_add_value)
            false
        } else {
            details_calorie.error = null
            true
        }
    }

    private fun validateFat(): Boolean {
        val fat = et_details_fat.text.toString().trim()

        return if (fat.isEmpty()) {
            details_fat.error = getString(R.string.error_add_value)
            false
        } else {
            details_fat.error = null
            true
        }
    }

    private fun validateCarb(): Boolean {
        val carb = et_details_carb.text.toString().trim()

        return if (carb.isEmpty()) {
            details_carb.error = getString(R.string.error_add_value)
            false
        } else {
            details_carb.error = null
            true
        }
    }

    private fun validateProt(): Boolean {
        val prot = et_details_prot.text.toString().trim()

        return if (prot.isEmpty()) {
            details_prot.error = getString(R.string.error_add_value)
            false
        } else {
            details_prot.error = null
            true
        }
    }

    private fun validateWeight(): Boolean {
        val weight = et_details_weight.text.toString().trim()

        return if (weight.isEmpty()) {
            details_weight.error = getString(R.string.error_add_value)
            false
        } else {
            details_weight.error = null
            true
        }
    }

    override fun onPause() {
        super.onPause()
        SearchActivity.isEditableFood = false
        finish()
    }
}