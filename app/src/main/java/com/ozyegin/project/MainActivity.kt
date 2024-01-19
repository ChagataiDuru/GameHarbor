package com.ozyegin.project

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var fragControl: MainFragmentDirections

    private lateinit var mainButton: Button
    private lateinit var searchButton: Button
    private lateinit var favoritesButton: Button
    private lateinit var profileButton: Button
    private var currentFragment: FragmentName = FragmentName.Main

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {}

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        if (isFirstTime()) {
            setContentView(R.layout.signup_fragment)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_signup) as NavHostFragment
            navController = navHostFragment.navController
            fragControl = MainFragmentDirections(navController)
            navigateToSignup()
        } else if (auth.currentUser == null) {
            setContentView(R.layout.login_fragment)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_login) as NavHostFragment
            navController = navHostFragment.navController
            fragControl = MainFragmentDirections(navController)
            navigateToLogin()
        } else {
            setupViews()
        }
    }

    private fun setupViews() {
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        fragControl = MainFragmentDirections(navController)

        mainButton = findViewById(R.id.main_button)
        searchButton = findViewById(R.id.search_button)
        favoritesButton = findViewById(R.id.favorite_button)
        profileButton = findViewById(R.id.profile_button)

        mainButton.setOnClickListener {
            fragControl.navigateToDestination(currentFragment, FragmentName.Main)
            currentFragment = FragmentName.Main
        }
    }

    private fun navigateToLogin() {
        fragControl.navigateToDestination(currentFragment, FragmentName.Login)
        currentFragment = FragmentName.Login
    }

    private fun navigateToSignup() {
        fragControl.navigateToDestination(currentFragment, FragmentName.Signup)
        currentFragment = FragmentName.Signup
    }

    private fun isFirstTime(): Boolean {
        val isFirstTime = sharedPreferences.getBoolean("firstTime", true)
        if (isFirstTime) {
            sharedPreferences.edit().putBoolean("firstTime", false).apply()
        }
        return isFirstTime
    }
}