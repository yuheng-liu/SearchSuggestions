package com.yuheng.searchsuggestions

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SearchSuggestionsRepo {

    private val searchSuggestions = emptyList<String>()
    private val searchSuggestionsFlow = MutableSharedFlow<List<String>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
        .apply { tryEmit(searchSuggestions) }

    fun getSearchSuggestions() = searchSuggestionsFlow.asSharedFlow().asLiveData(timeoutInMs = 0L)

    fun updateSearchSuggestions(searchSuggestions: List<String>) = searchSuggestionsFlow.tryEmit(searchSuggestions)

    fun clearSearchSuggestions() = searchSuggestionsFlow.tryEmit(emptyList())
}