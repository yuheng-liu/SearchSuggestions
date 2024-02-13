package com.yuheng.searchsuggestions.network

import com.yuheng.searchsuggestions.network.models.SearchQueryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DuckDuckGoApiService {

    @GET("ac")
    fun getSearchQuery(
        @Query("q") searchTerm: String,
        @Query("type") type: String = "list",
    ): Response<SearchQueryResponse>
}