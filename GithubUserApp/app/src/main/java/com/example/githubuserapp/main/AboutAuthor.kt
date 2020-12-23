package com.example.githubuserapp.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.R


class AboutAuthor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.author_detail)
        val fb = findViewById(R.id.facebook) as ImageView
        val ig = findViewById(R.id.instagram) as ImageView
        val li = findViewById(R.id.linkedin) as ImageView
        fb.setOnClickListener {
            Toast.makeText(applicationContext, "go to facebook", Toast.LENGTH_SHORT).show()
            val url = ("https://www.facebook.com/farhantandia8/")
            val openFb = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            startActivity(openFb)
        }
        ig.setOnClickListener {
            Toast.makeText(applicationContext, "go to instagram", Toast.LENGTH_SHORT).show()
            val url = ("https://www.instagram.com/farhantandia/")
            val openFb = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            startActivity(openFb)
        }
        li.setOnClickListener {
            Toast.makeText(applicationContext, "go to linkedin", Toast.LENGTH_SHORT).show()
            val url = ("https://www.linkedin.com/in/mfarhantandia/")
            val openFb = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            startActivity(openFb)
        }
        if (supportActionBar != null) {
            supportActionBar!!.setTitle(R.string.about_me)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }override fun onSupportNavigateUp(): Boolean {
        finish()
        onBackPressed()
        return true
    }
    
}

