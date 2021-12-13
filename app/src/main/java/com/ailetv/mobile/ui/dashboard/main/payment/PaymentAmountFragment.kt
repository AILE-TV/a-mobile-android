package com.ailetv.mobile.ui.dashboard.main.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ailetv.mobile.R
import com.ailetv.mobile.data.model.resource.ErrorDialogModel
import com.ailetv.mobile.databinding.FragmentPaymentAmountBinding
import com.ailetv.mobile.ui.EventBus
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.dialogs.ErrorDialog
import com.ailetv.mobile.utils.extensions.initToolbar
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PaymentAmountFragment : BaseFragment() {
    private val safeArgs by navArgs<PaymentAmountFragmentArgs>()
    override val viewModel by viewModel<PaymentAmountVM> {
        parametersOf(safeArgs.contractPojo)
    }
    private val binding by lazy { FragmentPaymentAmountBinding.inflate(layoutInflater) }

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

        KeyboardVisibilityEvent.registerEventListener(activity) {
            if (it)
                binding.motionLayout.transitionToEnd()
            else
                binding.motionLayout.transitionToStart()
        }

        safeArgs.contractPojo.contractType?.titleRes?.let { binding.titleTxt.setText(it) }
        safeArgs.contractPojo.contractType?.logoRes?.let { binding.logoImg.setImageResource(it) }
    }

    private fun initObservers() {
        viewModel.paymentCreated.observe(viewLifecycleOwner) {
            findNavController().navigate(
                PaymentAmountFragmentDirections.actionGlobalWebFragment(
                    getString(R.string.app_name),
                    it.first,
                    it.second,
                    false
                )
            )
        }

        viewModel.paymentSuccess.observe(viewLifecycleOwner) {
            ErrorDialog()
                .createDialog(requireActivity(), layoutInflater)
                .show(
                    requireActivity(),
                    ErrorDialogModel(
                        title = getString(R.string.toast_payment_successfully)
                    )
                )

            EventBus.refreshMain.value = true
            findNavController().navigateUp()
        }

        viewModel.paymentUnSuccess.observe(viewLifecycleOwner) {
            ErrorDialog()
                .createDialog(requireActivity(), layoutInflater)
                .show(
                    requireActivity(),
                    ErrorDialogModel(
                        title = getString(R.string.toast_payment_unsuccessfully)
                    )
                )

            findNavController().navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.paymentStatus()
    }

    override fun hideActionBar() = true
}