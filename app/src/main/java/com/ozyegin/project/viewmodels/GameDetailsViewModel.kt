package com.ozyegin.project.viewmodels

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozyegin.project.R
import com.ozyegin.project.data.Game

class GameDetailsViewModel(context: Context) : ViewModel() {
    private val resources: Resources = context.resources

    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game

    // Dummy game data
    private val dummyGame = Game(
        1,
        resources.getString(R.string.game_1_title),
        resources.getString(R.string.game_1_description),
        resources.getString(R.string.game_1_score).toInt(),
        resources.getString(R.string.game_1_user_reviews),
        listOf(resources.getString(R.string.game_1_screenshots)),
        resources.getString(R.string.game_1_links),
    )

    // Initialize game data
    init {
        _game.value = dummyGame
    }
}