package com.ailetv.mobile.ui.dashboard.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ailetv.mobile.data.enums.ContractTypeEnum
import com.ailetv.mobile.databinding.FragmentMainBinding
import com.ailetv.mobile.ui.EventBus
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.dialogs.ListDialog
import com.ailetv.mobile.utils.extensions.setAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {
    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    override val viewModel by viewModel<MainVM>()
    private val adapter by lazy {
        binding.recyclerView.setAdapter<ContractsAdapter>(ContractsAdapter())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        init()
        setClicks()
        initObservers()
        initAdapter()
        return binding.root
    }

    private fun init() {
        binding.recyclerView.itemAnimator = null
    }

    private fun setClicks() {
        binding.bonusBtn.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalBonusFragment(
                    viewModel.contractList.value!!.toTypedArray()
                )
            )
        }

        binding.addressBtn.setOnClickListener { showCustomers() }
    }

    private fun initObservers() {
        viewModel.contractList.observe(viewLifecycleOwner) {
            adapter.submitList(it) {
                binding.recyclerView.scrollToPosition(0)
            }
        }

        EventBus.refreshMain.observe(viewLifecycleOwner) {
            viewModel.getCustomerListData()
        }
    }

    private fun initAdapter() {
        adapter.onItemClick = {
            when (it.contractType) {
                ContractTypeEnum.INTERNET ->
                    findNavController().navigate(MainFragmentDirections.toInternetFragment(it))
                ContractTypeEnum.TV ->
                    findNavController().navigate(MainFragmentDirections.toTvFragment(it))
                ContractTypeEnum.IP_TV ->
                    findNavController().navigate(MainFragmentDirections.toIpTvFragment(it))
            }
        }
    }

    private fun showCustomers() {
        val list = viewModel.customerList.value

        val dialog = ListDialog(context)
        dialog.show(list?.map { it.address.toString() })

        dialog.onItemClick = { _, position ->
            list?.getOrNull(position)?.let {
                viewModel.setCustomerModel(it)
            }

            dialog.dismiss()
        }
    }


    override fun hideActionBar() = true
}