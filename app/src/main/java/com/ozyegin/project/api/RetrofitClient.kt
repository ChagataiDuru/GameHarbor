package com.ozyegin.project.api

import com.ozyegin.project.util.OAuthKeys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = OAuthKeys.PROXY_URL

    private val apiXKey = OAuthKeys.PROXY_API_KEY

    // Create an Interceptor to add headers
    private val headerInterceptor = Interceptor { chain ->
        val original: Request = chain.request()

        val request: Request = original.newBuilder()
            .header("x-api-key", apiXKey)
            .build()

        chain.proceed(request)
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val igdbService: IGDBService = retrofit.create(IGDBService::class.java)
}
