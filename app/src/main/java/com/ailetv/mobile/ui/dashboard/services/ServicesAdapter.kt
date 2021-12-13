package com.ailetv.mobile.ui.dashboard.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ailetv.mobile.data.model.resource.ServicePOJO
import com.ailetv.mobile.databinding.ItemServiceBinding
import com.ailetv.mobile.utils.bindingAdapters.setVisible

class ServicesAdapter :
    ListAdapter<ServicePOJO, ServicesAdapter.ItemHolder>(DiffCallback) {

    var onItemClick: (model: ServicePOJO) -> Unit = {}

    inner class ItemHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ServicePOJO, position: Int) {
            binding.model = model
            binding.executePendingBindings()

            binding.underline.setVisible(position != itemCount - 1)

            binding.root.setOnClickListener { onItemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(
            ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) =
        holder.bind(getItem(position), position)

    object DiffCallback : DiffUtil.ItemCallback<ServicePOJO>() {
        override fun areItemsTheSame(oldItem: ServicePOJO, newItem: ServicePOJO) =
            oldItem.serviceId == newItem.serviceId

        override fun areContentsTheSame(oldItem: ServicePOJO, newItem: ServicePOJO) =
            oldItem == newItem
    }
}