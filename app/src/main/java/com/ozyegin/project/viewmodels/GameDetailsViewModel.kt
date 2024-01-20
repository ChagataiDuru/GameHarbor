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
    val listsOfGame: MutableLiveData<List<ListEntity>> by lazy { MutableLiveData<List<ListEntity>>() }
    private val getListsOfGameError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    val getListError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    val getUserRateSuccessful: MutableLiveData<Int?> by lazy { MutableLiveData<Int?>() }
    val getUserRateError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    val deleteUserRateSuccessful: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val deleteUserRateError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }

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

    // Success getting the lists in which the game is included
    private val getListsOfGameSuccessfulFunction = Observer<List<ListEntity>> {
        listsOfGame.value = it
        clearInterfaceObservers()
    }

    // Error getting the lists in which the game is included
    private val getListsOfGameErrorFunction = Observer<Exception> {
        getListsOfGameError.value = it
        clearInterfaceObservers()
    }

    // Success getting the list
    private val getListSuccessfulFunction = Observer<List<ListEntity>> {
        userLists = it
        clearInterfaceObservers()
    }

    // Error getting the list
    private val getListErrorFunction = Observer<Exception> {
        getListError.value = it
        clearInterfaceObservers()
    }

    // Success getting user rate
    private val getUserRateSuccessfulFunction = Observer<Int?> {
        getUserRateSuccessful.value = it
        clearInterfaceObservers()
    }

    // Error getting user rate
    private val getUserRateErrorFunction = Observer<Exception> {
        getUserRateError.value = it
        clearInterfaceObservers()
    }

    // Success deleting user rate
    private val deleteUserRateSuccessfulFunction = Observer<Boolean> {
        getUserRateSuccessful.value = null
        deleteUserRateSuccessful.value = it
        clearInterfaceObservers()
    }

    // Error deleting user rate
    private val deleteUserRateErrorFunction = Observer<Exception> {
        deleteUserRateError.value = it
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