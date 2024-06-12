package com.example.trashure.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trashure.MainActivity
import com.example.trashure.R
import com.example.trashure.data.pref.UserPreference
import com.example.trashure.data.pref.dataStore
import com.example.trashure.ui.login.LoginActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            goToMainActivity()
        }, 5000L)
    }
    private fun goToMainActivity() {
        runBlocking {
            val userPreference = UserPreference.getInstance(this@SplashActivity.dataStore)
            val userModel = userPreference.getSession().first()

            val intent = if (userModel.isLogin) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}