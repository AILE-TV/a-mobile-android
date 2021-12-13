package com.ailetv.mobile.ui.dashboard.campaigns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ailetv.mobile.data.model.resource.CampaignModel
import com.ailetv.mobile.databinding.ItemCompanyBinding

class CampaignsAdapter :
    ListAdapter<CampaignModel, CampaignsAdapter.ItemHolder>(DiffCallback) {

    var onItemClick: (model: CampaignModel) -> Unit = {}

    inner class ItemHolder(private val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CampaignModel) {
            binding.model = model

            binding.itemCv.setOnClickListener { onItemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(
            ItemCompanyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<CampaignModel>() {
        override fun areItemsTheSame(oldItem: CampaignModel, newItem: CampaignModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CampaignModel,
            newItem: CampaignModel
        ) = oldItem == newItem
    }
}