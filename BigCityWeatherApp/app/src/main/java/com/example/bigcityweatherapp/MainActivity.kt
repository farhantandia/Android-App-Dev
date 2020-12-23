package com.example.bigcityweatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var rvCity: RecyclerView

    private var list: ArrayList<City> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCity = findViewById(R.id.rv_cities)
        rvCity.setHasFixedSize(true)

        list.addAll(BigCityData.listData)
        showRecyclerList()
        rvCity.setHasFixedSize(true)
        rvCity.layoutManager = LinearLayoutManager(this)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun showWeatherSearch() {
        val MoveIntent = Intent(this@MainActivity, WeatherSearch::class.java)
        startActivity(MoveIntent)
            }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }
    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.action_list -> {
                title = "20 World Largest Cities"
                showRecyclerList()
            }

            R.id.action_search -> {
                showWeatherSearch()
            }

            R.id.action_about-> {
                val MoveIntent = Intent(this@MainActivity, AboutAuthor::class.java)
                startActivity(MoveIntent)
            }
        }
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
    private fun showRecyclerList() {
        rvCity.layoutManager = LinearLayoutManager(this)
        val listbigcityAdapter = ListBigCity(list)
        rvCity.adapter = listbigcityAdapter

        listbigcityAdapter.setOnItemClickCallback(object : ListBigCity.OnItemClickCallback {
            override fun onItemClicked(data: City) {
                showSelectedCity(data)
                val MoveIntent = Intent(this@MainActivity, WeatherDetail::class.java)
                startActivity(MoveIntent)
            }
        })
    }
    private fun showSelectedCity(city:City) {
        Toast.makeText(this, "You choose " + city.name, Toast.LENGTH_SHORT).show()
    }
}