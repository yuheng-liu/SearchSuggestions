package com.yuheng.searchsuggestions.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DUCK_DUCK_GO_BASE_URL = "https://duckduckgo.com/"
object RetrofitServiceBuilder {

    fun getApiService(): DuckDuckGoApiService = getRetrofitInstance().create(DuckDuckGoApiService::class.java)

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    private fun getRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(DUCK_DUCK_GO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}