package com.example.trashure.ui.upload

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.trashure.R
import com.example.trashure.ViewModelFactory
import com.example.trashure.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private val viewModel by viewModels<UploadVM> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityResultBinding
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val image = intent.getStringExtra("image")
        image?.let {
            Glide.with(this)
                .load(Uri.parse(it))
                .into(binding.photo)
        }

        viewModel.getSession().observe(this) {
            token = it.email
            token?.let {
                viewModel.getResultTrash(it)
            }
        }

        valueResult()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun valueResult() {
        viewModel.resultResp.observe(this) {
            if (it != null) {
                it.data?.firstOrNull()?.let { value ->
                    val values = listOf(
                        "Cardboard" to value.cardboard,
                        "Glass" to value.glass,
                        "Metal" to value.metal,
                        "Paper" to value.paper,
                        "Plastic" to value.plastic
                    )

                    val sortedValues = values
                        .filter { it.second is Number }
                        .sortedByDescending { (it.second as Number).toFloat() }

                    val highest = sortedValues.firstOrNull()

                    highest.let {
                        val name = it?.first
                        val numericValue = it?.second as Number
                        val percentValue = numericValue.toFloat() * 100
                        val formattedValue = String.format("%.2f%%", percentValue)
                        binding.finalResult.text = "$name $formattedValue"
//                        binding.glass.text = formattedValue
                        Log.d(TAG, "tertinggi: $name - $formattedValue")
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "ResultActivity"
    }
}