package com.example.githubconsumerapp

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubconsumerapp.adapter.UserAdapter
import com.example.githubconsumerapp.adapter.UserModel
import com.example.githubconsumerapp.db.DatabaseContract.UserColumnns.Companion.CONTENT_URI
import com.example.githubconsumerapp.db.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity:AppCompatActivity() {

    private lateinit var rvUser: RecyclerView

    private lateinit var progressBar: ProgressBar
    private var users = arrayListOf<UserModel>()
    lateinit var listGithubUser: UserAdapter

    private lateinit var welcome_message: TextView
    companion object {
        private const val STATE_RESULT = "state_result"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = this.getString(R.string.consumerapp_title)
        }

        rvUser = findViewById(R.id.favorites_layout)
        rvUser.setHasFixedSize(true)
        rvUser.layoutManager = LinearLayoutManager(this)
        welcome_message = findViewById(R.id.welcome_message)

        progressBar = findViewById(R.id.progressBar)
        listGithubUser = UserAdapter(users)
        rvUser.adapter = listGithubUser

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                prepare()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            prepare()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserModel>(STATE_RESULT)
            if (list != null) {
                listGithubUser.data = list
                progressBar.visibility = View.INVISIBLE

            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE_RESULT, listGithubUser.data)
    }
    override fun onResume() {
        super.onResume()
        prepare()

    }
    private fun prepare() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgress(true)
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
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