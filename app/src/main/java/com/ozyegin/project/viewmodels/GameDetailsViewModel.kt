package com.ozyegin.project.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ozyegin.project.data.GameDetail as GameDetailEntity
import com.ozyegin.project.data.ListEntity
import com.ozyegin.project.api.IgdbRepository

class GameDetailsViewModel : ViewModel() {

    // IGDB instance
    private val igdbRepository = IgdbRepository()

    var gameId : String = ""
    private var userLists: List<ListEntity> = emptyList()


    // Livedata objects
    val gameDetails: MutableLiveData<GameDetailEntity> by lazy { MutableLiveData<GameDetailEntity>() }
    val getGameDetailError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }


    // Success getting game details
    private val getGameDetailSuccessfulFunction = Observer<GameDetailEntity> {
        gameDetails.value = it
        clearInterfaceObservers()
    }

    // Error getting game details
    private val getGameDetailErrorFunction = Observer<Exception> {
        getGameDetailError.value = it
        clearInterfaceObservers()
    }

    /**
     * Gets game details
     */
    fun getGameDetail() {
        igdbRepository.getGameDetailSuccessful.observeForever(getGameDetailSuccessfulFunction)
        igdbRepository.getGameDetailError.observeForever(getGameDetailErrorFunction)

        igdbRepository.getGameDetail(gameId)
    }

    private fun clearInterfaceObservers() {
        igdbRepository.getGameDetailSuccessful.removeObserver {}
        igdbRepository.getGameDetailError.removeObserver {}
    }
}