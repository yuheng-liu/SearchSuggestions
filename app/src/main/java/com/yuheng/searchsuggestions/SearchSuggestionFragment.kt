package com.yuheng.searchsuggestions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuheng.searchsuggestions.databinding.FragmentSearchSuggestionsBinding
import com.yuheng.searchsuggestions.network.DuckDuckGoApiService
import com.yuheng.searchsuggestions.network.RetrofitServiceBuilder
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
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration((DividerItemDecoration(requireContext(), layoutManager.orientation)))
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
                (activity as MainActivity).searchInBrowser(query ?: "")
                SearchSuggestionsRepo.clearSearchSuggestions()
                findNavController().popBackStack()
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
        binding.searchIcon.setOnClickListener { findNavController().popBackStack() }
    }

    private fun onSuggestionItemPressed(text: String) {
        binding.searchBar.setQuery(text, true)
    }

    private fun onChooseSuggestionPressed(text: String) {
        binding.searchBar.setQuery(text, false)
    }
}