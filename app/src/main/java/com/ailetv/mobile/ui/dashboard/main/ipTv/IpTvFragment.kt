package com.ailetv.mobile.ui.dashboard.main.ipTv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ailetv.mobile.databinding.FragmentIpTvBinding
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.initToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class IpTvFragment : BaseFragment() {
    private val safeArgs by navArgs<IpTvFragmentArgs>()
    override val viewModel by viewModel<IpTvVM> {
        parametersOf(safeArgs.contractPojo)
    }
    private val binding by lazy { FragmentIpTvBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        init()
        initObservers()
        return binding.root
    }

    private fun init() {
        initToolbar(binding.toolbar)

        binding.payBtn.setOnClickListener {
            findNavController().navigate(
                IpTvFragmentDirections.actionGlobalPaymentAmountFragment(safeArgs.contractPojo)
            )
        }
    }

    private fun initObservers() {

    }

    override fun hideActionBar() = true
}