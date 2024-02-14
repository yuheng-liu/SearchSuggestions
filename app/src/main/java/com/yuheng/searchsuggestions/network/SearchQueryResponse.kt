package com.yuheng.searchsuggestions.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchQueryResponse(
    @Json(name = "phrase") val phrase: String
)