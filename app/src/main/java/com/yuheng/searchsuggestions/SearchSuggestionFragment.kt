package com.yuheng.searchsuggestions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yuheng.searchsuggestions.databinding.FragmentSearchSuggestionsBinding
import com.yuheng.searchsuggestions.network.DuckDuckGoApiService
import com.yuheng.searchsuggestions.network.RetrofitServiceBuilder

class SearchSuggestionFragment : Fragment() {

    private lateinit var binding: FragmentSearchSuggestionsBinding
    private lateinit var apiService: DuckDuckGoApiService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchSuggestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiService = RetrofitServiceBuilder.getApiService()
        setupListeners()
    }

    private fun setupListeners() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                apiService.getSearchQuery(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}