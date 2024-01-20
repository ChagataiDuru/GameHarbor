package com.ozyegin.project.api

import androidx.lifecycle.MutableLiveData
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import com.ozyegin.project.api.RetrofitClient.igdbService
import com.ozyegin.project.util.IgdbConts
import com.ozyegin.project.util.OAuthKeys
import proto.Game
import retrofit2.Call
import com.ozyegin.project.data.GameDetail as GameDetailEntity
import com.ozyegin.project.data.GameDetailClass as GameEntityTransformer
import com.ozyegin.project.data.GameList as GameListEntity


class IgdbRepository {

    // MutableLiveData variables for IGDB games
    val getTrendingGamesSuccessful: MutableLiveData<List<GameListEntity>> by lazy { MutableLiveData<List<GameListEntity>>() }
    val getTrendingGamesError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    val getSearchResultSuccessful: MutableLiveData<List<GameListEntity>> by lazy { MutableLiveData<List<GameListEntity>>() }
    val getSearchResultError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    val getGameDetailSuccessful: MutableLiveData<GameDetailEntity> by lazy { MutableLiveData<GameDetailEntity>() }
    val getGameDetailError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }


    /**
     * Gets the data of the selected game
     */
    fun getGameListData(gameId: String): GameListEntity {
        IGDBWrapper.setupProxy(OAuthKeys.PROXY_URL, mapOf("x-api-key" to OAuthKeys.PROXY_API_KEY))
        val apicalypse = APICalypse()
            .fields(IgdbConts.GAME_LIST_ENTITY_FIELDS)
            .where("id=".plus(gameId))

        try {
            val igdbGames = IGDBWrapper.games(apicalypse)
            return GameEntityTransformer.convertFromGameToGameListEntity(igdbGames[0])

        } catch (e: RequestException) {
            throw e
        }
    }

    /**
     * Gets the trending games
     */
    fun getTrendingGames() {
        val call = igdbService.getTrendingGames(
            fields = IgdbConts.GAME_LIST_ENTITY_FIELDS,
            where = "total_rating>=60 & first_release_date>=1650391492",
            limit = 10,
            sort = "hypes desc"
        )

        call.enqueue(object : retrofit2.Callback<List<Game>> {
            override fun onResponse(
                call: Call<List<Game>>,
                response: retrofit2.Response<List<Game>>
            ) {
                if (response.isSuccessful) {
                    getTrendingGamesSuccessful.value =
                        response.body()?.sortedByDescending { it.totalRating }?.map { game ->
                            GameEntityTransformer.convertFromGameToGameListEntity(game)
                        }
                } else {
                    getTrendingGamesError.value = Exception(response.message())
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                getTrendingGamesError.value = Exception(t.message)
            }
        })
    }

    /**
     * Gets the details of the selected game
     */
    fun getGameDetail(gameId: String) {
        val call = igdbService.getGameDetail(
            fields = IgdbConts.GAME_DETAIL_ENTITY_FIELDS,
            where = "id=".plus(gameId)
        )
        call.enqueue(object : retrofit2.Callback<List<Game>> {
            override fun onResponse(
                call: Call<List<Game>>,
                response: retrofit2.Response<List<Game>>
            ) {
                if (response.isSuccessful) {
                    getGameDetailSuccessful.value =
                        GameEntityTransformer.convertFromGameToGameDetailEntity(response.body()!![0])
                } else {
                    getGameDetailError.value = Exception(response.message())
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                getGameDetailError.value = Exception(t.message)
            }
        })
    }

    /**
     * Searches for games by the selected game name
     */
    fun searchGamesByName(gameName: String) {
        val call = igdbService.searchGamesByName(
            fields = IgdbConts.GAME_LIST_ENTITY_FIELDS,
            search = gameName,
            limit = 20
        )

        call.enqueue(object : retrofit2.Callback<List<Game>> {
            override fun onResponse(
                call: Call<List<Game>>,
                response: retrofit2.Response<List<Game>>
            ) {
                if (response.isSuccessful) {
                    getSearchResultSuccessful.value =
                        response.body()?.sortedByDescending { it.totalRating }?.map { game ->
                            GameEntityTransformer.convertFromGameToGameListEntity(game)
                        }
                } else {
                    getSearchResultError.value = Exception(response.message())
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                getSearchResultError.value = Exception(t.message)
            }
        })
    }
}