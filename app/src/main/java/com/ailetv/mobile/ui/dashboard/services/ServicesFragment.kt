package com.ailetv.mobile.ui.dashboard.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ailetv.mobile.databinding.FragmentServicesBinding
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.setAdapter

class ServicesFragment : BaseFragment() {
    override val viewModel by viewModel<ServicesVM>()
    private val binding by lazy { FragmentServicesBinding.inflate(layoutInflater) }

    private val adapter by lazy {
        binding.recyclerView.setAdapter<ServicesAdapter>(ServicesAdapter())
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
        adapter.onItemClick = {

        }
    }

    override fun hideActionBar() = true

    override fun onStart() {
        super.onStart()

        viewModel.getServices()
    }
}