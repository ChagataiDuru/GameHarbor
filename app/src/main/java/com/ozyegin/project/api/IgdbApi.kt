package com.ozyegin.project.api

import androidx.lifecycle.MutableLiveData
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.IGDBWrapper.apiProtoRequest
import com.api.igdb.request.games
import com.ozyegin.project.data.GameDetail as GameDetailEntity
import com.ozyegin.project.data.GameList as GameListEntity
import com.ozyegin.project.data.GameDetailClass as GameEntityTransformer
import com.ozyegin.project.util.IgdbConts
import com.ozyegin.project.util.OAuthKeys
import proto.Game

class IgdbRepository {

     // MutableLiveData variables for IGDB games
     val getTrendingGamesSuccessful: MutableLiveData<List<GameListEntity>> by lazy { MutableLiveData<List<GameListEntity>>() }
     val getTrendingGamesError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
     val getSearchResultSuccessful: MutableLiveData<List<GameListEntity>> by lazy { MutableLiveData<List<GameListEntity>>() }
     val getSearchResultError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
     val getGameDetailSuccessful: MutableLiveData<GameDetailEntity> by lazy { MutableLiveData<GameDetailEntity>() }
     val getGameDetailError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
     val getGamesNamesError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }


    /**
     * Gets the data of the selected game
     */
     fun getGameListData(gameId: String) : GameListEntity {
        IGDBWrapper.setCredentials(OAuthKeys.clientID, OAuthKeys.accessToken)
        val apicalypse = APICalypse()
            .fields(IgdbConts.GAME_LIST_ENTITY_FIELDS)
            .where("id=".plus(gameId))

        try {
            val igdbGames = IGDBWrapper.games(apicalypse)
            return GameEntityTransformer.convertFromGameToGameListEntity(igdbGames[0])

        } catch(e: RequestException) {
            throw e
        }
    }

    /**
     * Gets the trending games
     */
     fun getTrendingGames() {
        IGDBWrapper.setCredentials(OAuthKeys.clientID, OAuthKeys.accessToken)
        println("Trending games are games with rating>60 and released in last 2 weeks")
        val apicalypse = APICalypse()
            .fields(IgdbConts.GAME_LIST_ENTITY_FIELDS)
            .limit(10)
            .offset(0)
            .search("Halo")
            .sort("release_dates.date", Sort.ASCENDING)
            .where("platforms = 48")
        val igdbGames: List<Game>
        try {
            println("Getting trending games")
            igdbGames = IGDBWrapper.games(apicalypse)
            println("Got trending games")
        } catch (e: Exception) {
            getTrendingGamesError.value = e
            return
        }
        try {
            println(igdbGames.size)
            println(igdbGames.get(0).name)
            println(igdbGames.get(0).totalRating)
            getTrendingGamesSuccessful.value = igdbGames.sortedByDescending { it.totalRating }.map { game ->
                GameEntityTransformer.convertFromGameToGameListEntity(game)
            }
            println(getTrendingGamesSuccessful.value)

        } catch(e: RequestException) {
            getTrendingGamesError.value = e
        }
    }

    /**
     * Gets the details of the selected game
     */
     fun getGameDetail(gameId: String) {
        IGDBWrapper.setCredentials(OAuthKeys.clientID, OAuthKeys.accessToken)
        val apicalypse = APICalypse()
            .fields(IgdbConts.GAME_DETAIL_ENTITY_FIELDS)
            .where("id=".plus(gameId))

        try {
            val igdbGames = IGDBWrapper.games(apicalypse)
            getGameDetailSuccessful.value = GameEntityTransformer.convertFromGameToGameDetailEntity(igdbGames[0])

        } catch(e: RequestException) {
            getGameDetailError.value = e
        }
    }

    /**
     * Searches for games by the selected game name
     */
     fun searchGamesByName(gameName: String) {
        IGDBWrapper.setCredentials(OAuthKeys.clientID, OAuthKeys.accessToken)
        val apicalypse = APICalypse()
            .fields(IgdbConts.GAME_LIST_ENTITY_FIELDS)
            .search(gameName)
            .limit(20)

        try {
            val igdbGames = IGDBWrapper.games(apicalypse)
            getSearchResultSuccessful.value = igdbGames.sortedByDescending { it.totalRating }.map { game ->
                GameEntityTransformer.convertFromGameToGameListEntity(game)
            }

        } catch(e: RequestException) {
            getSearchResultError.value = e
        }
    }

}