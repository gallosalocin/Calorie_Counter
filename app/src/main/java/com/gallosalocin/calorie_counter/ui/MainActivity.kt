package com.gallosalocin.calorie_counter.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gallosalocin.calorie_counter.R
import kotlinx.android.synthetic.main.activity_day.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.meal_toolbar

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var dayTag = 0
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(meal_toolbar)

        dayChoice()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_toolbar_set_bmr -> Intent(this, BmrActivity::class.java).also {
                startActivity(it)
            }
        }
        return true
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.monday -> Intent(this, DayActivity::class.java).also {
                dayTag = 1
                startActivity(it)
            }
            R.id.tuesday -> Intent(this, DayActivity::class.java).also {
                dayTag = 2
                startActivity(it)
            }
            R.id.wednesday -> Intent(this, DayActivity::class.java).also {
                dayTag = 3
                startActivity(it)
            }
            R.id.thursday -> Intent(this, DayActivity::class.java).also {
                dayTag = 4
                startActivity(it)
            }
            R.id.friday -> Intent(this, DayActivity::class.java).also {
                dayTag = 5
                startActivity(it)
            }
            R.id.saturday -> Intent(this, DayActivity::class.java).also {
                dayTag = 6
                startActivity(it)
            }
            R.id.sunday -> Intent(this, DayActivity::class.java).also {
                dayTag = 7
                startActivity(it)
            }
        }
    }

    private fun dayChoice() {
        monday.setOnClickListener(this)
        tuesday.setOnClickListener(this)
        wednesday.setOnClickListener(this)
        thursday.setOnClickListener(this)
        friday.setOnClickListener(this)
        saturday.setOnClickListener(this)
        sunday.setOnClickListener(this)
    }




}