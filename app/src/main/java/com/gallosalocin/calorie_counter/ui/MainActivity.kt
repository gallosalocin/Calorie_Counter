package com.gallosalocin.calorie_counter.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.models.User
import com.gallosalocin.calorie_counter.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userViewModel: UserViewModel
    private var user: User? = User()

    companion object {
        var dayTag = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        dayChoice()


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getUser.observe(this, androidx.lifecycle.Observer {
            user = it
        })
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
            R.id.main_language -> {
                showChangeLanguage()
            }
        }
        return true
    }

    private fun showChangeLanguage() {
        val languageList = arrayOf(getString(R.string.english), getString(R.string.french))

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.choose_language))
            .setIcon(R.drawable.ic_language_black)
            .setSingleChoiceItems(languageList, -1) { dialog, which ->
                if (which == 0) {
                    setLocate("en")
                    recreate()
                } else if (which == 1) {
                    setLocate("fr")
                    recreate()
                }
                dialog.dismiss()
            }.create().show()
    }

    private fun setLocate(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Language", language)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Language", "")
        if (language != null) {
            setLocate(language)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
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