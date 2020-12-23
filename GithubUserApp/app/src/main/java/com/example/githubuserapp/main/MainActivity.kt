package com.example.githubuserapp.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.model.ResponseUser
import com.example.githubuserapp.model.UserModel
import com.example.githubuserapp.parsingAPI.ApiClient.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    var dataGithub: List<UserModel> = ArrayList()
    private lateinit var rvUser: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchImg: ImageView
    private lateinit var searchText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.title = getString(R.string.search_user)
        }
        progressBar = findViewById(R.id.progressBar)
        searchImg = findViewById(R.id.search_icon)
        searchText = findViewById(R.id.search_text)

        rvUser = findViewById(R.id.rv_search_user)
        rvUser.setHasFixedSize(true)
        rvUser.layoutManager = LinearLayoutManager(this)
        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            findViewById<View>(R.id.sv_user) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.username)
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                showProgress(true)
                getDataOnline(s)

                searchImg.setVisibility(View.INVISIBLE);
                searchText.setVisibility(View.INVISIBLE);
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return true
            }
        })
    }

    fun showProgress(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun getDataOnline(usernames: String) {
        val request = apiService.getSearchUser(usernames)
        request.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(
                call: Call<ResponseUser>,
                response: Response<ResponseUser>
            ) {
                if (response.isSuccessful) {
                    dataGithub = response.body()?.items!!
                    rvUser.adapter = UserAdapter(dataGithub as ArrayList<UserModel>)
                    showProgress(false)
                } else {
                    Toast.makeText(this@MainActivity, "Request Not Success", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(
                call: Call<ResponseUser>,t: Throwable
            ) {
                Toast.makeText(this@MainActivity, "Request Failure" + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        } else if (item.itemId == R.id.action_about) {
            val mIntent2 = Intent(this@MainActivity, AboutAuthor::class.java)
            startActivity(mIntent2)
        }else if (item.itemId == R.id.advanced_setting){
            val mIntent3 = Intent(this@MainActivity, ReminderActivity::class.java)
            startActivity(mIntent3)
        }else if (item.itemId == R.id.favorite){
            val mIntent4 = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(mIntent4)
        }
        return super.onOptionsItemSelected(item)
    }
}
