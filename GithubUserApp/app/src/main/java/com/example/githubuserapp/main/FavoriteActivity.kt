package com.example.githubuserapp.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.HandlerThread
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.db.DatabaseContract.UserColumnns.Companion.CONTENT_URI
import com.example.githubuserapp.db.FavoriteHelper
import com.example.githubuserapp.db.MappingHelper
import com.example.githubuserapp.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView
    private val waitingTime = 50
    private var counter: CountDownTimer? = null
    private var mSearchQuery: String? = null
    private var defaultText = ""
    private var users = arrayListOf<UserModel>()
    private lateinit var listGithubUser : UserAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    private lateinit var progressBar: ProgressBar
    private lateinit var welcome_message: TextView
    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Favorite User"
        }

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        rvUser = findViewById(R.id.favorites_layout)
        rvUser.setHasFixedSize(true)
        listGithubUser = UserAdapter(users)
        rvUser.adapter = listGithubUser

        progressBar = findViewById(R.id.progressBar)
        welcome_message = findViewById(R.id.welcome_message)
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                prepare(defaultText)
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        if (savedInstanceState == null) {
            prepare(defaultText)
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserModel>(STATE_RESULT)
            if (list != null) {
                listGithubUser.data = list
                progressBar.visibility = View.INVISIBLE

            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        onBackPressed()
        return true
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE_RESULT, listGithubUser.data)
    }

    override fun onResume() {
        super.onResume()
        prepare(defaultText)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.favorite_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_favorites).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mSearchQuery = query
                Toast.makeText(this@FavoriteActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mSearchQuery = newText
                counter?.cancel()
                counter = object : CountDownTimer(waitingTime.toLong(), 20) {
                    override fun onTick(millisUntilFinished: Long) {
                        Log.d(
                            "TIME",
                            "seconds remaining: " + millisUntilFinished / 1000
                        )
                    }

                    override fun onFinish() {
                        Log.d("FINISHED", "DONE")
                        prepare(newText)
                    }
                }
                (counter as CountDownTimer).start()
                return false
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        if (item.itemId == R.id.advanced_setting) {
            val moveIntent = Intent(this, ReminderActivity::class.java)
            startActivity(moveIntent)
        }
        if (item.itemId == R.id.action_about) {
            val moveIntent = Intent(this, AboutAuthor::class.java)
            startActivity(moveIntent)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun prepare(newText: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgress(true)
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = favoriteHelper.querybyUserName(newText)
                MappingHelper.mapCursorToArrayList(cursor)

            }
            showProgress(false)

            users = deferredNotes.await()
            if (users.size > 0) {
                showRecyclerList()
                welcome_message.visibility=View.INVISIBLE

            } else {
                showRecyclerList()

            }
        }

    }

    private fun showProgress(state: Boolean) { if (state) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }

    }

    private fun showRecyclerList() {

        rvUser.layoutManager = LinearLayoutManager(this)
        listGithubUser =UserAdapter(users)
        rvUser.adapter = listGithubUser

    }

}
