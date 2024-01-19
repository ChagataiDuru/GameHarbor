package com.ozyegin.project.api

import androidx.lifecycle.MutableLiveData
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import com.ozyegin.project.data.GameDetailEntity
import com.ozyegin.project.data.GameListEntity
import com.ozyegin.project.data.GameDetailClass
import com.ozyegin.project.util.IgdbConts
import com.ozyegin.project.util.OAuthKeys

class IgdbRepository {

    // MutableLiveData variables for IGDB games
    override val getTrendingGamesSuccessful: MutableLiveData<List<GameListEntity>> by lazy { MutableLiveData<List<GameListEntity>>() }
    override val getTrendingGamesError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    override val getSearchResultSuccessful: MutableLiveData<List<GameListEntity>> by lazy { MutableLiveData<List<GameListEntity>>() }
    override val getSearchResultError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    override val getGameDetailSuccessful: MutableLiveData<GameDetailEntity> by lazy { MutableLiveData<GameDetailEntity>() }
    override val getGameDetailError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    override val getGamesNamesError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }


    /**
     * Gets the data of the selected game
     */
    override fun getGameListData(gameId: String) : GameListEntity {
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
    override fun getTrendingGames() {
        IGDBWrapper.setCredentials(OAuthKeys.clientID, OAuthKeys.accessToken)
        // Trending games are games with rating>60 and released in last 2 weeks
        val apicalypse = APICalypse()
            .fields(IgdbConts.GAME_LIST_ENTITY_FIELDS)
            .where("total_rating>=60 & first_release_date>=1650391492")
            .limit(10)
            .sort("hypes", Sort.DESCENDING)

        try {
            val igdbGames = IGDBWrapper.games(apicalypse)
            getTrendingGamesSuccessful.value = igdbGames.sortedByDescending { it.totalRating }.map { game ->
                GameEntityTransformer.convertFromGameToGameListEntity(game)
            }

        } catch(e: RequestException) {
            getTrendingGamesError.value = e
        }
    }

    /**
     * Gets the details of the selected game
     */
    override fun getGameDetail(gameId: String) {
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