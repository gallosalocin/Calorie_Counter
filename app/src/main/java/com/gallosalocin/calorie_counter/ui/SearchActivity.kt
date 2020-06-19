package com.gallosalocin.calorie_counter.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var allFoodList: List<Food>
    private lateinit var food: Food


    companion object {
        var isEditableFood = false
        const val EXTRA_FOOD = "food extras"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        configToolbar()
        setupFabCreateFood()
        setupSearchEditText()

        setupRecyclerView()
        configItemTouchHelper()

    }

    private fun setupRecyclerView() {
        allFoodList = ArrayList()

        foodAdapter = FoodAdapter()
        rv_search.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
        }
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
                    startActivity(it)
                }
            }

            override fun setOnLongClickListener(position: Int) {
                Toast.makeText(applicationContext, foodAdapter.differ.currentList[position].name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun configItemTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                food = foodAdapter.differ.currentList[position]

                if (direction == ItemTouchHelper.RIGHT) {
                    foodViewModel.deleteFood(food)
                    et_search.text?.clear()
                    Snackbar.make(
                        rv_search,
                        (getString(R.string.Successfully_remove_food, food.name)),
                        Snackbar.LENGTH_LONG
                    ).apply {
                        setAction(getString(R.string.undo_snackbar)) {
                            foodViewModel.insertFood(food)
                        }
                        show()
                    }
                } else {
                    Intent(this@SearchActivity, MealActivity::class.java).also {
//                        val food: Food = allFoodList[position]
//                   if (MainActivity.dayTag == 1 && DayActivity.mealTag == 1){
                        MealActivity.foodMealsList.add(food)
//                   }
                        startActivity(it)
                        Snackbar.make(rv_search, (getString(R.string.Successfully_add_food, food.name)), Snackbar.LENGTH_LONG).show()
                    }
                    finish()
                }
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
                foodViewModel.allFoodsByProtein.observe(this, androidx.lifecycle.Observer { foods ->
                    allFoodList = foods
                    foodAdapter.differ.submitList(allFoodList)
                })
            }
            R.id.search_filter_carb -> categoryFilter(R.string.carbohydrate)
            R.id.search_filter_veggies -> categoryFilter(R.string.veggies)
            R.id.search_filter_fruits -> categoryFilter(R.string.fruits)
            R.id.search_filter_healthy_fats -> categoryFilter(R.string.healthy_fats)
            R.id.search_filter_oils -> categoryFilter(R.string.oils)
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

    override fun onPause() {
        super.onPause()
        et_search.text?.clear()
    }

}
