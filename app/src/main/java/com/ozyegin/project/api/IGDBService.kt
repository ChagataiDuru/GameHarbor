package com.ozyegin.project.api

import proto.Game
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface IGDBService {

    @POST("games")
    fun getTrendingGames(
        @Query("fields") fields: String,
        @Query("where") where: String,
        @Query("limit") limit: Int,
        @Query("sort") sort: String
    ): Call<List<Game>>

    @POST("games")
    fun searchGamesByName(
        @Query("fields") fields: String,
        @Query("search") search: String,
        @Query("limit") limit: Int
    ): Call<List<Game>>

    @POST("games")
    fun getGameDetail(
        @Query("fields") fields: String,
        @Query("where") where: String
    ): Call<List<Game>>
}
