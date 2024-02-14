package com.yuheng.searchsuggestions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuheng.searchsuggestions.databinding.FragmentSearchSuggestionsBinding
import com.yuheng.searchsuggestions.network.DuckDuckGoApiService
import com.yuheng.searchsuggestions.network.RetrofitServiceBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchSuggestionFragment : Fragment() {

    private lateinit var binding: FragmentSearchSuggestionsBinding
    private lateinit var apiService: DuckDuckGoApiService

    private lateinit var suggestionsAdapter: SuggestionsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchSuggestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiService = RetrofitServiceBuilder.getApiService()
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        suggestionsAdapter = SuggestionsAdapter(
            { suggestionText -> onSuggestionItemPressed(suggestionText) },
            { suggestionText -> onChooseSuggestionPressed(suggestionText) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = suggestionsAdapter
    }

    private fun setupObservers() {
        SearchSuggestionsRepo.getSearchSuggestions().observe(viewLifecycleOwner) { suggestions ->
            suggestionsAdapter.submitList(suggestions)
        }
    }

    private fun setupListeners() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewLifecycleOwner.lifecycleScope.launch {
                    val response = apiService.getSearchQuery(newText ?: "")
                    if (response.isSuccessful) {
                        val suggestionsList = response.body()!!
                        SearchSuggestionsRepo.updateSearchSuggestions(suggestionsList.map { it.phrase })
                    }
                }
                return true
            }
        })
    }

    private fun onSuggestionItemPressed(text: String) {

    }

    private fun onChooseSuggestionPressed(text: String) {

    }
}