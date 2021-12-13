package com.ailetv.mobile.ui.dashboard.myAccount

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ailetv.mobile.databinding.FooterMyAccountBinding
import com.ailetv.mobile.manager.SessionManager

class MyAccountAdapter : ListAdapter<String, MyAccountAdapter.ItemHolder>(DiffCallback) {
    var onContactUsClick: (phoneNumber: String) -> Unit = {}
    var onLiveTvClick: () -> Unit = {}
    var onWhatsappClick: () -> Unit = {}
    var onFacebookClick: () -> Unit = {}
    var onInstagramClick: () -> Unit = {}
    var onLogoutClick: () -> Unit = {}

    inner class ItemHolder(private val binding: FooterMyAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.phoneEdt.setText(SessionManager.phoneNumber)

            binding.contactUsBtn.setOnClickListener {
                onContactUsClick(binding.contactUsBtn.text.toString())
            }
            binding.liveTvBtn.setOnClickListener { onLiveTvClick() }
            binding.whatsappBtn.setOnClickListener { onWhatsappClick() }
            binding.facebookBtn.setOnClickListener { onFacebookClick() }
            binding.instagramBtn.setOnClickListener { onInstagramClick() }
            binding.logoutBtn.setOnClickListener { onLogoutClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(
            FooterMyAccountBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.bind()
    override fun getItemCount() = 1

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}