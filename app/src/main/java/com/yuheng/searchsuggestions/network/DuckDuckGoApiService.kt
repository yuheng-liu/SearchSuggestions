package com.yuheng.searchsuggestions.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DuckDuckGoApiService {

    @GET("ac")
    suspend fun getSearchQuery(
        @Query("q") searchTerm: String,
        @Query("type") type: String = "json",
    ): Response<List<SearchQueryResponse>>
}