package com.ailetv.mobile.ui.dashboard.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ailetv.mobile.data.model.resource.NotificationPOJO
import com.ailetv.mobile.databinding.ItemNotificationBinding
import com.ailetv.mobile.utils.bindingAdapters.setVisible

class NotificationsAdapter :
    ListAdapter<NotificationPOJO, NotificationsAdapter.ItemHolder>(DiffCallback) {

    var onItemClick: (model: NotificationPOJO) -> Unit = {}
    var onDeleteClick: (model: NotificationPOJO) -> Unit = {}

    inner class ItemHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: NotificationPOJO, position: Int) {
            binding.model = model

            binding.underline.setVisible(position != itemCount - 1)

            binding.root.setOnClickListener { onItemClick(model) }
            binding.deleteBtn.setOnClickListener { onDeleteClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    object DiffCallback : DiffUtil.ItemCallback<NotificationPOJO>() {
        override fun areItemsTheSame(oldItem: NotificationPOJO, newItem: NotificationPOJO) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NotificationPOJO,
            newItem: NotificationPOJO
        ) = oldItem == newItem
    }
}