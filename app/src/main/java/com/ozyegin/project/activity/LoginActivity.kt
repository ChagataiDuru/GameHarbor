package com.ozyegin.project.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ozyegin.project.R
import com.ozyegin.project.databinding.ActivityLoginBinding
import com.ozyegin.project.LoginFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_login, LoginFragment())
                .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()

        // Check if the user is logged. Go Main activity instead
        if (FirebaseAuth.getInstance().currentUser != null) {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            finish()
        }
    }
}