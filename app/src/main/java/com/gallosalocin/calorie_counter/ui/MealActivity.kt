package com.gallosalocin.calorie_counter.ui

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.adapters.FoodAdapter
import com.gallosalocin.calorie_counter.models.Food
import com.gallosalocin.calorie_counter.viewmodel.FoodViewModel
import com.google.android.material.snackbar.Snackbar
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
        toolbarMealName(MainActivity.dayTag, DayActivity.mealTag)
        setupFabAddFood()

        setupRecycleView()
        setupListAndMacrosPerMeal()
        configItemTouchHelper()


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
            setHasFixedSize(true)
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@MealActivity)
        }
    }

    private fun setupListAndMacrosPerMeal() {
        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        foodViewModel.allFoodsByDayAndMeal.observe(this, androidx.lifecycle.Observer { foods ->
            allFoodByDayAndMealList = foods

            meal_cal_total.text = String.format("%.0f", allFoodByDayAndMealList.sumByDouble { it.calorie.toDouble() })
            meal_fat_total.text = String.format("%.1f", allFoodByDayAndMealList.sumByDouble { it.fat.toDouble() })
            meal_carb_total.text = String.format("%.1f", allFoodByDayAndMealList.sumByDouble { it.carb.toDouble() })
            meal_prot_total.text = String.format("%.1f", allFoodByDayAndMealList.sumByDouble { it.prot.toDouble() })

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
                    Snackbar.make(rv_meal, (getString(R.string.successfully_remove_food, food.name)), Snackbar.LENGTH_LONG).apply {
                        setAction(getString(R.string.undo_snackbar)) {
                            foodViewModel.insertFood(food)
                        }
                        show()
                    }
                }
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val removeIcon: Drawable = ContextCompat.getDrawable(this@MealActivity, R.drawable.ic_remove_swipe)!!
                val swipeRightBackground = ColorDrawable(Color.parseColor("#FF9900"))
                val itemView = viewHolder.itemView
                val removeIconMargin = (itemView.height - removeIcon.intrinsicHeight) / 2

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (dX > 0) {
                        swipeRightBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                        removeIcon.setBounds(
                            itemView.left + removeIconMargin,
                            itemView.top + removeIconMargin,
                            itemView.left + removeIconMargin + removeIcon.intrinsicWidth,
                            itemView.bottom - removeIconMargin
                        )
                        swipeRightBackground.draw(canvas)
                        removeIcon.draw(canvas)
                    }
                }
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_meal)
        }
    }

    private fun toolbarMealName(dayTag: Int, mealTag: Int) {
        val dayTagArray = arrayOf(
            getString(R.string.monday_cap), getString(R.string.tuesday_cap), getString(R.string.wednesday_cap), getString(R.string.thursday_cap),
            getString(R.string.friday_cap), getString(R.string.saturday_cap), getString(R.string.sunday_cap)
        )
        val mealTagArray = arrayOf(
            getString(R.string.breakfast_cap), getString(R.string.lunch_cap), getString(R.string.dinner_cap), getString(R.string.snack_cap))

        this.title = dayTagArray[dayTag - 1] + " / " + mealTagArray[mealTag - 1]
    }
}