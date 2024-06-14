package com.example.trashure.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.trashure.MainActivity
import com.example.trashure.R
import com.example.trashure.data.pref.UserPreference
import com.example.trashure.data.pref.dataStore
import com.example.trashure.onboarding.OnBoardingActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        // Check user session
        runBlocking {
            val userPreference = UserPreference.getInstance(this@SplashActivity.dataStore)
            val userModel = userPreference.getSession().first()

            if (userModel.isLogin) {
                // If user is logged in, show splash screen
                setContentView(R.layout.activity_splash)
                Handler(Looper.getMainLooper()).postDelayed({
                    goToMainActivity()
                }, 5000L)
            } else {
                // If user is not logged in, go to OnBoardingActivity
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
