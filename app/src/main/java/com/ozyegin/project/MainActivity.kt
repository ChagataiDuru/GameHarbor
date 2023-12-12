package com.ozyegin.project

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var gamesButton: Button;
    private lateinit var playlistButton: Button;
    private lateinit var reviewsButton: Button;
    private lateinit var listsButton: Button;
    private var currentFragment: FragmentName = FragmentName.Main;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        gamesButton = findViewById(R.id.games_page_button);
        playlistButton = findViewById(R.id.playlist_page_button);
        reviewsButton = findViewById(R.id.reviews_page_button);
        listsButton = findViewById(R.id.lists_page_button);

        gamesButton.setOnClickListener {
            navigateToDestination(currentFragment, FragmentName.Games);
            currentFragment = FragmentName.Games;
        }
        playlistButton.setOnClickListener {
            navigateToDestination(currentFragment, FragmentName.Playlist);
            currentFragment = FragmentName.Playlist;
        }
        reviewsButton.setOnClickListener {
            navigateToDestination(currentFragment, FragmentName.Reviews);
            currentFragment = FragmentName.Reviews;
        }
        listsButton.setOnClickListener {
            navigateToDestination(currentFragment, FragmentName.Lists);
            currentFragment = FragmentName.Lists;
        }

    }
}