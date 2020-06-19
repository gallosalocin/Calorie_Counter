package com.gallosalocin.calorie_counter.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.adapters.FoodAdapter
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.utils.Category
import com.gallosalocin.calorie_counter.viewmodel.FoodViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_search.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var foodViewModel: FoodViewModel
    private lateinit var food: Food

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        configToolbar()
        configSpinner()

        isEditableFood()
        configEnterButtonSoftKeyboard()

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
            onGetExtras()
        }
    }

    private fun onGetExtras() {
        food = intent.getSerializableExtra(SearchActivity.EXTRA_FOOD) as Food

        et_details_name.setText(food.name)
//        spinner_category.setSelection(food.category.toInt())
        et_details_calorie.setText(food.calorie.toString())
        et_details_weight.setText(food.weight.toString())
        et_details_fat.setText(food.fat.toString())
        et_details_carb.setText(food.carb.toString())
        et_details_prot.setText(food.prot.toString())
        details_note.setText(food.note)
    }

    private fun saveOrUpdateFood() {

        val saveOrUpdateFood = Food(
            et_details_name.text.toString(),
            spinner_category.selectedItem.toString(),
            spinner_category.tag as Int,
            details_note.text.toString(),
            et_details_calorie.text.toString().toInt(),
            et_details_weight.text.toString().toInt(),
            et_details_fat.text.toString().toFloat(),
            et_details_carb.text.toString().toFloat(),
            et_details_prot.text.toString().toFloat()
        )

        if (SearchActivity.isEditableFood) {
            saveOrUpdateFood.id = food.id
            foodViewModel.updateFood(saveOrUpdateFood)
            SearchActivity.isEditableFood = false
            Toast.makeText(this, "Aliment actualisé", Toast.LENGTH_SHORT).show()
        } else {
            foodViewModel.insertFood(saveOrUpdateFood)
            Toast.makeText(this, "Aliment ajouté", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configSpinner() {
        val categoryList: MutableList<Category> = ArrayList()

        categoryList.add(Category(getString(R.string.choose_category), 0xFFFFFFFF.toInt()))
        categoryList.add(Category("Protéines", 0xFFE57373.toInt()))
        categoryList.add(Category("Glucides", 0xFFFFF176.toInt()))
        categoryList.add(Category("Légumes", 0xFF48B34D.toInt()))
        categoryList.add(Category("Fruits", 0xFF9575CD.toInt()))
        categoryList.add(Category("Graisses saines", 0xFF4DD0E1.toInt()))
        categoryList.add(Category("Huiles", 0xFFC1A36E.toInt()))

        val adapter = ArrayAdapter(this, R.layout.spinner_custom_layout, categoryList)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner_category.adapter = adapter

        spinner_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val category = adapterView?.getItemAtPosition(position) as Category
                spinner_category.tag = category.categoryColor
            }
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
        if (!validateName() || !validateCategory() || !validateCalorie() || !validateFat() || !validateCarb() || !validateProt() || !validateWeight()) {
            return
        }
        saveOrUpdateFood()
        finish()
    }

    private fun validateName() : Boolean {
        val name = et_details_name.text.toString().trim()

        return if (name.isEmpty()) {
            details_name.error = getString(R.string.error_add_value)
            false
        } else {
            details_name.error = null
            true
        }
    }

    private fun validateCategory() : Boolean {
        val category  = spinner_category.selectedItem.toString().trim()
        val errorText: TextView = spinner_category.selectedView as TextView

        return if (category == getString(R.string.choose_category)) {
            errorText.error = getString(R.string.error_add_value)
            false
        } else {
            errorText.error = null
            true
        }
    }

    private fun validateCalorie() : Boolean {
        val calorie = et_details_calorie.text.toString().trim()

        return if (calorie.isEmpty()) {
            details_calorie.error = getString(R.string.error_add_value)
            false
        } else {
            details_calorie.error = null
            true
        }
    }

    private fun validateFat() : Boolean {
        val fat = et_details_fat.text.toString().trim()

        return if (fat.isEmpty()) {
            details_fat.error = getString(R.string.error_add_value)
            false
        } else {
            details_fat.error = null
            true
        }
    }

    private fun validateCarb() : Boolean {
        val carb = et_details_carb.text.toString().trim()

        return if (carb.isEmpty()) {
            details_carb.error = getString(R.string.error_add_value)
            false
        } else {
            details_carb.error = null
            true
        }
    }

    private fun validateProt() : Boolean {
        val prot = et_details_prot.text.toString().trim()

        return if (prot.isEmpty()) {
            details_prot.error = getString(R.string.error_add_value)
            false
        } else {
            details_prot.error = null
            true
        }
    }

    private fun validateWeight() : Boolean {
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