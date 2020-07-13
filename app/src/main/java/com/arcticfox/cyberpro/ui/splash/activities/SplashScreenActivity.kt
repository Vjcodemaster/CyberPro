package com.arcticfox.cyberpro.ui.splash.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.arcticfox.cyberpro.R
import com.arcticfox.cyberpro.ui.home.activities.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            /*val i = Intent(this@SplashScreenActivity, HomeActivity::class.java)
            startActivity(i)
            finish()*/
            startActivity(Intent(this@SplashScreenActivity,HomeActivity::class.java))
            finish()
        }, 1000)
    }
}