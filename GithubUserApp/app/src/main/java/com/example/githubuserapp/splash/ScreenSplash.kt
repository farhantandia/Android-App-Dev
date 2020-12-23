package com.example.githubuserapp.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.R
import com.example.githubuserapp.main.MainActivity

@Suppress("DEPRECATION")
class ScreenSplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hiding title bar of this activity
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.screen_splash)

        //4second splash time
        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this@ScreenSplash, MainActivity::class.java))
            //finish this activity
            finish()
        },1500)

    }
}
