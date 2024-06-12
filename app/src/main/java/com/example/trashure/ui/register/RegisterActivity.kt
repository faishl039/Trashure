package com.example.trashure.ui.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trashure.R
import com.example.trashure.ViewModelFactory
import com.example.trashure.databinding.ActivityRegisterBinding
import com.example.trashure.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressDialog = ProgressDialog(this).apply {
            setTitle("Registering")
            setMessage("Please wait...")
            setCancelable(false)
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            register()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }

        viewModel.isSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                showSuccessDialog()
            }
        }

        viewModel.registerResult.observe(this) { result ->
            if (result == null) {
                showErrorDialog()
            }
        }
    }

    private fun register() {
        val name = binding.fullName.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.passwordConf.text.toString()

        if (password != confirmPassword) {
            binding.passwordConf.error = "Konfirmasi password tidak cocok"
            return
        }

        viewModel.register(name, email, password)
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Akun dengan ${binding.email.text} sudah jadi nih. Yuk, login!")
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage("Registrasi gagal. Silakan coba lagi.")
            setPositiveButton("OK", null)
            create()
            show()
        }
    }
}
