package com.ailetv.mobile.utils.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ailetv.mobile.databinding.ListItemTextBinding

class ChooseItemAdapter : ListAdapter<String, ChooseItemAdapter.ItemHolder>(DiffCallback) {
    var onItemClick: ((String, Int) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ListItemTextBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ItemHolder(val binding: ListItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String, position: Int) {
            binding.titleTxt.text = text
            binding.root.setOnClickListener { onItemClick?.invoke(text, position) }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}