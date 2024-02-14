package com.yuheng.searchsuggestions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yuheng.searchsuggestions.databinding.SuggestionItemBinding

class SuggestionsAdapter(
    private val itemListener: (String) -> Unit,
    private val chooseSuggestionListener: (String) -> Unit,
): ListAdapter<String, SuggestionsAdapter.SuggestionViewHolder>(SuggestionsDiffCallback()) {

    class SuggestionViewHolder(private val binding: SuggestionItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(suggestionText: String, itemListener: (String) -> Unit, chooseSuggestionListener: (String) -> Unit) = with(binding) {
            textSearchSuggestion.text = suggestionText
            buttonChooseText.setOnClickListener { chooseSuggestionListener(suggestionText) }
            root.setOnClickListener { itemListener(suggestionText) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder(SuggestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(getItem(position), itemListener, chooseSuggestionListener)
    }

    override fun submitList(list: List<String>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }
}

private class SuggestionsDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem.contentEquals(newItem)
}