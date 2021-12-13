package com.ailetv.mobile.ui.dashboard.campaigns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ailetv.mobile.R
import com.ailetv.mobile.databinding.FragmentCampaignsBinding
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.setAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CampaignsFragment : BaseFragment() {
    override val viewModel by viewModel<CampaignsVM>()
    private val binding by lazy { FragmentCampaignsBinding.inflate(layoutInflater) }
    val adapter by lazy { binding.recyclerView.setAdapter<CampaignsAdapter>(CampaignsAdapter()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        init()
        initAdapter()
        return binding.root
    }

    private fun init() {
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun initAdapter() {
        adapter.onItemClick = {
            findNavController().navigate(
                CampaignsFragmentDirections.actionGlobalWebFragment(
                    getString(R.string.app_name),
                    it.redirectUrl ?: "", null, true
                )
            )
        }
    }

    override fun hideActionBar() = true
}