package com.example.trashure.ui.article

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trashure.R

class DetailArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_article)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        val article = intent.getParcelableExtra<Article>("Article")
        if (article != null) {
            val textView = findViewById<TextView>(R.id.tvJudulArtc)
            val desc1 = findViewById<TextView>(R.id.tvValueArtc1)
            val desc2 = findViewById<TextView>(R.id.tvValueArtc2)
            val desc3 = findViewById<TextView>(R.id.tvValueArtc3)
            val imageView = findViewById<ImageView>(R.id.ivArtc)

            textView.text = article.name
            desc1.text=article.description1
            desc2.text=article.description2
            desc3.text=article.description3

            imageView.setImageResource(article.photo)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // Ends the current activity and returns to the previous activity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}