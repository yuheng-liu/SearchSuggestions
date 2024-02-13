package com.yuheng.searchsuggestions.network.models

import com.google.gson.annotations.SerializedName

data class SearchQueryResponse(
    @SerializedName("results") val results: Map<String, List<String>>
)