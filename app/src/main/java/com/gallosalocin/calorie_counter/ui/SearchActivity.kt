package com.gallosalocin.calorie_counter.ui

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var allFoodList: List<Food>
    private lateinit var food: Food

    companion object {
        var isEditableMacros = false
        var isEditableFood = false
        var isInvisible = false
        var isNewFood = false
        const val EXTRA_FOOD = "food extras"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        configToolbar()
        setupFabCreateFood()
        setupSearchEditText()
        setupRecyclerView()

        setupListLiveData()
        configItemTouchHelper()
        et_search.requestFocus()
    }

    private fun setupRecyclerView() {
        allFoodList = ArrayList()

        foodAdapter = FoodAdapter()
        rv_search.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
        }
    }

    private fun setupListLiveData() {
        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        foodViewModel.allFoods.observe(this, androidx.lifecycle.Observer { foods ->
            allFoodList = foods
            foodAdapter.differ.submitList(allFoodList)
        })
        onItemClickListener()
    }

    private fun onItemClickListener() {
        foodAdapter.setOnItemClickListener(object : FoodAdapter.OnItemClickListener {
            override fun setOnClickListener(position: Int) {
                Intent(this@SearchActivity, DetailsActivity::class.java).also {
                    food = foodAdapter.differ.currentList[position]
                    it.putExtra(EXTRA_FOOD, food)
                    isEditableFood = true
                    isInvisible = true
                    startActivity(it)
                }
                Toast.makeText(applicationContext, foodAdapter.differ.currentList[position].name, Toast.LENGTH_SHORT).show()
            }

            override fun setOnLongClickListener(position: Int) {
                Intent(this@SearchActivity, DetailsActivity::class.java).also {
                    food = foodAdapter.differ.currentList[position]
                    it.putExtra(EXTRA_FOOD, food)
                    isEditableMacros = true
                    startActivity(it)
                }
                Toast.makeText(applicationContext, foodAdapter.differ.currentList[position].name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun configItemTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                food = foodAdapter.differ.currentList[position]

                if (direction == ItemTouchHelper.RIGHT) {

                    AlertDialog.Builder(this@SearchActivity)
                        .setCancelable(false)
                        .setTitle(getString(R.string.title_alert_dialog))
                        .setIcon(R.drawable.ic_delete_swipe_black)
                        .setMessage(getString(R.string.delete_alert_dialog_question, food.name))
                        .setPositiveButton(getString(R.string.yes_alert_dialog)) { _, _ ->
                            foodViewModel.deleteFood(food)
                            et_search.text?.clear()
                            Snackbar.make(rv_search, (getString(R.string.successfully_remove_food, food.name)), Snackbar.LENGTH_LONG).apply {
                                setAction(getString(R.string.undo_snackbar)) {
                                    foodViewModel.insertFood(food)
                                }
                                show()
                            }
                        }
                        .setNegativeButton(getString(R.string.no_alert_dialog)) { _, _ ->
                            et_search.text?.clear()
                            setupRecyclerView()
                            setupListLiveData()
                        }.create().show()
                } else {
                    Intent(this@SearchActivity, MealActivity::class.java).also {
                        food.dayMealId = MainActivity.dayTag.toString() + DayActivity.mealTag.toString()
                        foodViewModel.duplicateFood(food.dayMealId, food.id)
                        startActivity(it)
                        Snackbar.make(rv_search, (getString(R.string.successfully_add_food, food.name)), Snackbar.LENGTH_LONG).show()
                    }
                    finish()
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
                val deleteIcon: Drawable = ContextCompat.getDrawable(this@SearchActivity, R.drawable.ic_delete_swipe)!!
                val addIcon: Drawable = ContextCompat.getDrawable(this@SearchActivity, R.drawable.ic_add_swipe)!!
                val swipeRightBackground = ColorDrawable(Color.parseColor("#FF0000"))
                val swipeLeftBackground = ColorDrawable(Color.parseColor("#00CC00"))
                val itemView = viewHolder.itemView
                val deleteIconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                val addIconMargin = (itemView.height - addIcon.intrinsicHeight) / 2

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    if (dX > 0) {
                        swipeRightBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                        deleteIcon.setBounds(
                            itemView.left + deleteIconMargin,
                            itemView.top + deleteIconMargin,
                            itemView.left + deleteIconMargin + deleteIcon.intrinsicWidth,
                            itemView.bottom - deleteIconMargin
                        )
                        swipeRightBackground.draw(canvas)
                        deleteIcon.draw(canvas)
                    } else {
                        swipeLeftBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                        addIcon.setBounds(
                            itemView.right - addIconMargin - addIcon.intrinsicWidth, itemView.top + addIconMargin, itemView.right - addIconMargin,
                            itemView.bottom - addIconMargin
                        )
                        swipeLeftBackground.draw(canvas)
                        addIcon.draw(canvas)
                    }

                }
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_search)
        }
    }

    private fun configToolbar() {
        setSupportActionBar(search_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_sort_category -> {
                foodViewModel.allFoodsByCategory.observe(this, androidx.lifecycle.Observer { foods ->
                    allFoodList = foods
                    foodAdapter.differ.submitList(allFoodList)
                })
            }
            R.id.search_sort_name -> {
                foodViewModel.allFoods.observe(this, androidx.lifecycle.Observer { foods ->
                    allFoodList = foods
                    foodAdapter.differ.submitList(allFoodList)
                })
            }
            R.id.search_filter_prot -> {
                foodViewModel.allFoodsCategoryProteins.observe(this, androidx.lifecycle.Observer { foods ->
                    allFoodList = foods
                    foodAdapter.differ.submitList(allFoodList)
                })
            }
            R.id.search_filter_carb -> {
                foodViewModel.allFoodsCategoryCarbs.observe(this, androidx.lifecycle.Observer { foods ->
                    allFoodList = foods
                    foodAdapter.differ.submitList(allFoodList)
                })
            }
            R.id.search_filter_veggies -> {
                foodViewModel.allFoodsCategoryVeggies.observe(this, androidx.lifecycle.Observer { foods ->
                    allFoodList = foods
                    foodAdapter.differ.submitList(allFoodList)
                })
            }
            R.id.search_filter_fruits -> {
                foodViewModel.allFoodsCategoryFruits.observe(this, androidx.lifecycle.Observer { foods ->
                    allFoodList = foods
                    foodAdapter.differ.submitList(allFoodList)
                })
            }
            R.id.search_filter_healthy_fats -> {
                foodViewModel.allFoodsCategoryHealthyFats.observe(this, androidx.lifecycle.Observer { foods ->
                    allFoodList = foods
                    foodAdapter.differ.submitList(allFoodList)
                })
            }
            R.id.search_filter_oils -> {
                foodViewModel.allFoodsCategoryOils.observe(this, androidx.lifecycle.Observer { foods ->
                    allFoodList = foods
                    foodAdapter.differ.submitList(allFoodList)
                })
            }
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setupSearchEditText() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                filter(editable.toString())
            }
        })
    }

    private fun filter(text: String) {
        val filteredFood = ArrayList<Food>()

        allFoodList.filterTo(filteredFood) {
            it.name.toLowerCase().contains(text.toLowerCase())
        }
        foodAdapter.filterList(filteredFood)
    }

    private fun setupFabCreateFood() {
        fab_create_food.setOnClickListener {
            Intent(this, DetailsActivity::class.java).also {
                isNewFood = true
                startActivity(it)
            }
        }
    }

    private fun categoryFilter(category: Int) {
        val filteredFood = ArrayList<Food>()

        allFoodList.filterTo(filteredFood) {
            it.category == getString(category)
        }
        foodAdapter.filterList(filteredFood)
    }
}
