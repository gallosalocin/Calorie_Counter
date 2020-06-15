package com.gallosalocin.calorie_counter.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.utils.Category
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item_food.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        configToolbar()

        configSpinner()
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
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun saveCreatedFood() {
        SearchActivity.allFoodList.add(
            Food(
                details_name.editText?.text.toString(),
                spinner_category.selectedItem.toString(),
                spinner_category.tag as Int,
                "",
                details_calorie.editText?.text.toString().toInt(),
                details_weight.editText?.text.toString().toInt(),
                details_fat.editText?.text.toString().toFloat(),
                details_carb.editText?.text.toString().toFloat(),
                details_prot.editText?.text.toString().toFloat()
            )
        )
    }

    private fun configSpinner() {
        val categoryList: MutableList<Category> = ArrayList()

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
                spinner_category.tag = category.categoryColor
            }
        }
    }

    private fun confirmInput() {
        if (!validateName() || !validateCategory() || !validateCalorie() || !validateFat() || !validateCarb() || !validateProt() || !validateWeight()) {
            return
        }
        saveCreatedFood()
        finish()
        Toast.makeText(this, getString(R.string.food_saved), Toast.LENGTH_SHORT).show()
    }

    private fun validateName() : Boolean {
        val name = details_name.editText?.text.toString().trim()

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
        val calorie = details_calorie.editText?.text.toString().trim()

        return if (calorie.isEmpty()) {
            details_calorie.error = getString(R.string.error_add_value)
            false
        } else {
            details_calorie.error = null
            true
        }
    }

    private fun validateFat() : Boolean {
        val fat = details_fat.editText?.text.toString().trim()

        return if (fat.isEmpty()) {
            details_fat.error = getString(R.string.error_add_value)
            false
        } else {
            details_fat.error = null
            true
        }
    }

    private fun validateCarb() : Boolean {
        val carb = details_carb.editText?.text.toString().trim()

        return if (carb.isEmpty()) {
            details_carb.error = getString(R.string.error_add_value)
            false
        } else {
            details_carb.error = null
            true
        }
    }

    private fun validateProt() : Boolean {
        val prot = details_prot.editText?.text.toString().trim()

        return if (prot.isEmpty()) {
            details_prot.error = getString(R.string.error_add_value)
            false
        } else {
            details_prot.error = null
            true
        }
    }

    private fun validateWeight() : Boolean {
        val weight = details_weight.editText?.text.toString().trim()

        return if (weight.isEmpty()) {
            details_weight.error = getString(R.string.error_add_value)
            false
        } else {
            details_weight.error = null
            true
        }
    }
}