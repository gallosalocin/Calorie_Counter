package com.gallosalocin.calorie_counter.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.adapters.FoodAdapter
import com.gallosalocin.calorie_counter.models.Food
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    companion object {
        lateinit var allFoodList: MutableList<Food>
    }

    private lateinit var foodAdapter: FoodAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        configToolbar()
        setupFabCreateFood()

        allFoodList = ArrayList()
        addFood()
        setupRecyclerView()
        setupSearchEditText()
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
                allFoodList.sortBy{ it.category.toLowerCase(Locale.ROOT) }
                setupRecyclerView()
            }
            R.id.search_sort_name -> {
                setupRecyclerView()
                allFoodList.sortBy { it.name.toLowerCase(Locale.ROOT) }
            }
            R.id.search_filter_prot -> categoryFilter(R.string.proteins)
            R.id.search_filter_carb  -> categoryFilter(R.string.carbohydrate)
            R.id.search_filter_veggies -> categoryFilter(R.string.veggies)
            R.id.search_filter_fruits -> categoryFilter(R.string.fruits)
            R.id.search_filter_healthy_fats -> categoryFilter(R.string.healthy_fats)
            R.id.search_filter_oils -> categoryFilter(R.string.oils)
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setupSearchEditText() {
        search.addTextChangedListener(object : TextWatcher {
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


    private fun setupRecyclerView() {
        foodAdapter = FoodAdapter()
        rv_search.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            foodAdapter.submitList(allFoodList)
        }
    }

    private fun setupFabCreateFood() {
        fab_create_food.setOnClickListener {
            Intent(this, DetailsActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun addFood() {
        allFoodList.add(Food("Blanc de poulet", getString(R.string.proteins), 0xFFE57373.toInt(), "", 162, 150, 2F, 0.7F, 35.3F))
        allFoodList.add(Food("Jambon", getString(R.string.proteins), 0xFFE57373.toInt(), "", 162, 150, 2F, 0.7F, 35.3F))
        allFoodList.add(Food("Coco Plats", getString(R.string.veggies), 0xFF48B34D.toInt(), "", 75, 250, 0.5F, 9.1F, 4.6F))
        allFoodList.add(Food("Carottes", getString(R.string.veggies), 0xFF48B34D.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Huile d'Olive", getString(R.string.oils), 0xFFC1A36E.toInt(), "", 90, 10, 10F, 0F, 0F))
        allFoodList.add(Food("Carottes", getString(R.string.veggies), 0xFF48B34D.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Huile d'Olive", getString(R.string.oils), 0xFFC1A36E.toInt(), "", 90, 10, 10F, 0F, 0F))
        allFoodList.add(Food("Carottes", getString(R.string.veggies), 0xFF48B34D.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Carottes", getString(R.string.veggies), 0xFF48B34D.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Feta", getString(R.string.healthy_fats), 0xFF4DD0E1.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Comte", getString(R.string.healthy_fats), 0xFF4DD0E1.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Pomme", getString(R.string.fruits), 0xFF9575CD.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Ananas", getString(R.string.fruits), 0xFF9575CD.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Tomates", getString(R.string.veggies), 0xFF48B34D.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Tomates", getString(R.string.veggies), 0xFF48B34D.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Glace", getString(R.string.carbohydrate), 0xFFFFF176.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
        allFoodList.add(Food("Glace", getString(R.string.carbohydrate), 0xFFFFF176.toInt(), "", 34, 100, 0.4F, 7.7F, 0.5F))
    }

    private fun categoryFilter(category: Int) {
        val filteredFood = ArrayList<Food>()

        allFoodList.filterTo(filteredFood) {
            it.category == getString(category)
        }
        foodAdapter.filterList(filteredFood)
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
        allFoodList.sortBy { it.name.toLowerCase(Locale.ROOT) }
    }

}
