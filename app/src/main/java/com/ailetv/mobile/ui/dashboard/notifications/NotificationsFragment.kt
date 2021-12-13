package com.ailetv.mobile.ui.dashboard.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ailetv.mobile.databinding.FragmentNotificationsBinding
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.setAdapter

class NotificationsFragment : BaseFragment() {
    private val binding by lazy { FragmentNotificationsBinding.inflate(layoutInflater) }
    override val viewModel: NotificationsVM by viewModel()

    private val adapter by lazy {
        binding.recyclerView.setAdapter<NotificationsAdapter>(NotificationsAdapter())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        init()
        initObservers()
        initAdapter()
        return binding.root
    }

    private fun init() {

    }

    private fun initObservers() {
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun initAdapter() {
        adapter.onDeleteClick = {
            viewModel.delete(it)
        }

        adapter.onItemClick = {
            if (it.isSeen?.get() == false)
                viewModel.markAsRead(it)
        }
    }

    override fun hideActionBar() = true

    override fun onStart() {
        super.onStart()

        viewModel.getNotifications()
    }
}