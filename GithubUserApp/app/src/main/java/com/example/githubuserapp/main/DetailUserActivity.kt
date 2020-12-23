@file:Suppress("DEPRECATION")

package com.example.githubuserapp.main

import android.app.ProgressDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.PageAdapter
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.db.DatabaseContract
import com.example.githubuserapp.db.DatabaseContract.UserColumnns.Companion.CONTENT_URI
import com.example.githubuserapp.db.FavoriteHelper
import com.example.githubuserapp.model.DetailUserModel
import com.example.githubuserapp.model.UserModel
import com.example.githubuserapp.parsingAPI.ApiClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import org.parceler.Parcels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {
    private lateinit var dataDetailUser : DetailUserModel
    private lateinit var dataUser : UserModel
    private lateinit var tvName : TextView
    private lateinit var tvUsername : TextView
    private lateinit var tvLocation : TextView
    private lateinit var tvCompany : TextView
    private lateinit var ivAvatar : ImageView
    private lateinit var favoriteHelper: FavoriteHelper
    private var statusFavorite: Boolean = false
    private lateinit var uriWithId: Uri
    private lateinit var FavButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        supportActionBar?.setTitle(getString(R.string.detail_user))
        val bundle = intent.getBundleExtra(UserAdapter.DATA_EXTRA)
        dataUser = Parcels.unwrap<UserModel>(bundle?.getParcelable(UserAdapter.DATA_USER))
        ivAvatar = findViewById(R.id.iv_avatar_detail)
        tvUsername = findViewById(R.id.tv_username_detail)
        tvName = findViewById(R.id.tv_name_detail)
        tvLocation = findViewById(R.id.tv_location_detail)
        tvCompany = findViewById(R.id.tv_company_detail)
        FavButton = findViewById(R.id.fav)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        val progress = ProgressDialog(this@DetailUserActivity)
        progress.setMessage(getString(R.string.progress))
        progress.show()

        Glide.with(this@DetailUserActivity)
            .load(dataUser.avatarUrl)
            .into(ivAvatar)
        tvUsername.setText(dataUser.login)

        //Memanggil API
        val request: Call<DetailUserModel> = ApiClient.apiService.getDetailUser(dataUser.login)
        request.enqueue(object : Callback<DetailUserModel> {
            override fun onResponse(
                call: Call<DetailUserModel>,
                response: Response<DetailUserModel>
            ) {
                dataDetailUser = response.body()!!
                tvName.setText(dataDetailUser.name)
                tvLocation.setText(dataDetailUser.location)
                tvCompany.setText(dataDetailUser.company)
                progress.dismiss()
            }

            override fun onFailure(
                call: Call<DetailUserModel>,
                t: Throwable
            ) {
            }
        })

        val pageAdapter = PageAdapter(this, supportFragmentManager)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = pageAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)
        supportActionBar!!.elevation = 0f



        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + dataUser.id)
        val cursor = contentResolver?.query(uriWithId, null, null, null, null)

        if (cursor != null) {
            statusFavorite = cursor.count > 0
        }
        cursor?.close()

        setStatusFavorite(statusFavorite)

        FavButton.setOnClickListener {
            statusFavorite = !statusFavorite
            if (statusFavorite) {
                val values = ContentValues()
                values.put(DatabaseContract.UserColumnns.COLUMN_NAME_USERNAME, dataUser.login)
                values.put(DatabaseContract.UserColumnns.COLUMN_NAME_AVATAR_URL, dataUser.avatarUrl)
                values.put(DatabaseContract.UserColumnns.COLUMN_USER_URL, dataUser.url)
                values.put(DatabaseContract.UserColumnns.COLUMN_NAME_USER_ID, dataUser.id)
                uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + dataUser.id)
                contentResolver.insert(uriWithId, values)
                contentResolver?.notifyChange(CONTENT_URI, null)
                Toast.makeText(
                    this,
                    getString(R.string.favorite_add_success),
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + dataUser.id)
                contentResolver.delete(uriWithId, null, null)
                contentResolver?.notifyChange(CONTENT_URI, null)
                Toast.makeText(
                    this,
                    getString(R.string.favorite_delete_success),
                    Toast.LENGTH_SHORT
                ).show()

            }

            setStatusFavorite(statusFavorite)
        }
    }
    private fun setStatusFavorite(statusFavorite: Boolean){
        if(statusFavorite){
            FavButton.setImageResource(R.drawable.ic_baseline_star_24)

        }

        else{
            FavButton.setImageResource(R.drawable.ic_baseline_star_border_24)
    }

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        onBackPressed()
        return true
    }
}