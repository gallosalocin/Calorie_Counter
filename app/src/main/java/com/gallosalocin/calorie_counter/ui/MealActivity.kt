package com.gallosalocin.calorie_counter.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.adapters.FoodAdapter
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.viewmodel.FoodViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_day.meal_toolbar
import kotlinx.android.synthetic.main.activity_meal.*

class MealActivity : AppCompatActivity() {

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var allFoodByDayAndMealList: List<Food>
    private lateinit var food: Food

    companion object {
        var isEditableFood = false
        var isInvisible = false
        const val EXTRA_FOOD = "food extras"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        configToolbar()
        toolbarMealName()
        setupFabAddFood()

        setupRecycleView()

        setupListAndMacrosPerMeal()
        configItemTouchHelper()


        Log.d("nico", "onCreate")

    }

    private fun configToolbar() {
        setSupportActionBar(meal_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupFabAddFood() {
        fab_meal_food_add.setOnClickListener {
            Intent(this, SearchActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun setupRecycleView() {
        allFoodByDayAndMealList = ArrayList()

        foodAdapter = FoodAdapter()
        rv_meal.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@MealActivity)
        }
    }

    private fun setupListAndMacrosPerMeal() {
        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        foodViewModel.allFoodsByDayAndMeal.observe(this, androidx.lifecycle.Observer { foods ->
            allFoodByDayAndMealList = foods
            overall_meal_cal_current.text = allFoodByDayAndMealList.sumBy { it.calorie }.toString()
            overall_meal_fat_current.text = String.format("%.1f", allFoodByDayAndMealList.sumByDouble { it.fat.toDouble() })
            overall_meal_carb_current.text = String.format("%.1f", allFoodByDayAndMealList.sumByDouble { it.carb.toDouble() })
            overall_meal_prot_current.text = String.format("%.1f", allFoodByDayAndMealList.sumByDouble { it.prot.toDouble() })
            foodAdapter.differ.submitList(allFoodByDayAndMealList)
        })
        onItemClickListener()
    }

    private fun onItemClickListener() {
        foodAdapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {

            override fun setOnClickListener(position: Int) {
                Intent(this@MealActivity, DetailsActivity::class.java).also {
                    food = foodAdapter.differ.currentList[position]
                    it.putExtra(EXTRA_FOOD, food)
                    isEditableFood = true
                    isInvisible = true
                    startActivity(it)
                }
                Toast.makeText(applicationContext, foodAdapter.differ.currentList[position].name, Toast.LENGTH_SHORT).show()
            }

            override fun setOnLongClickListener(position: Int) {
                Toast.makeText(applicationContext, foodAdapter.differ.currentList[position].name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun configItemTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                food = foodAdapter.differ.currentList[position]

                if (direction == ItemTouchHelper.RIGHT) {
                    foodViewModel.deleteFood(food)
                    Snackbar.make(rv_meal, (getString(R.string.Successfully_remove_food, food.name)), Snackbar.LENGTH_LONG).apply {
                        setAction(getString(R.string.undo_snackbar)) {
                            foodViewModel.insertFood(food)
                        }
                        show()
                    }
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_meal)
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
}