package com.ozyegin.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController;
    private lateinit var fragControl: MainFragmentDirections;

    private lateinit var mainButton: Button;
    private lateinit var searchButton: Button;
    private lateinit var favoritesButton: Button;
    private var currentFragment: FragmentName = FragmentName.Main;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {}


        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        fragControl = MainFragmentDirections(navController)

        mainButton = findViewById(R.id.main_button);
        searchButton = findViewById(R.id.search_button);
        favoritesButton = findViewById(R.id.favorite_button);

        mainButton.setOnClickListener {
            fragControl.navigateToDestination(currentFragment, FragmentName.Main);
            currentFragment = FragmentName.Main;
        }
        searchButton.setOnClickListener {
            fragControl.navigateToDestination(currentFragment, FragmentName.Search);
            currentFragment = FragmentName.Search;
        }
        favoritesButton.setOnClickListener {
            fragControl.navigateToDestination(currentFragment, FragmentName.Favorites);
            currentFragment = FragmentName.Favorites;
        }

    }
}