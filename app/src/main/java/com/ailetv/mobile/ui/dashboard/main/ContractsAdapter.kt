package com.ailetv.mobile.ui.dashboard.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ailetv.mobile.data.model.resource.ContractPOJO
import com.ailetv.mobile.databinding.ItemContractBinding
import com.ailetv.mobile.databinding.SectionMainBinding

class ContractsAdapter :
    ListAdapter<ContractPOJO, RecyclerView.ViewHolder>(DiffCallback) {

    var onItemClick: (model: ContractPOJO) -> Unit = {}

    inner class SectionHolder(binding: SectionMainBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemHolder(private val binding: ItemContractBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ContractPOJO) {
            binding.model = model

            binding.root.setOnClickListener { onItemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            0 -> SectionHolder(
                SectionMainBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> ItemHolder(
                ItemContractBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder)
            holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int) =
        if (getItem(position) == null) 0 else 1

    object DiffCallback : DiffUtil.ItemCallback<ContractPOJO>() {
        override fun areItemsTheSame(oldItem: ContractPOJO, newItem: ContractPOJO) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ContractPOJO, newItem: ContractPOJO) =
            oldItem == newItem
    }

    override fun submitList(list: List<ContractPOJO?>?) {
        val mutableList = list?.toMutableList()
        mutableList?.add(0, null)
        super.submitList(mutableList)
    }

    override fun submitList(list: List<ContractPOJO?>?, commitCallback: Runnable?) {
        val mutableList = list?.toMutableList()
        mutableList?.add(0, null)
        super.submitList(mutableList, commitCallback)
    }
}