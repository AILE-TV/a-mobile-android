package com.ailetv.mobile.ui.dashboard.main.bonus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ailetv.mobile.R
import com.ailetv.mobile.databinding.FragmentBonusBinding
import com.ailetv.mobile.manager.SessionManager
import com.ailetv.mobile.ui.EventBus
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.dialogs.ListDialog
import com.ailetv.mobile.utils.extensions.initToolbar
import com.ailetv.mobile.utils.extensions.onBackPressedCallback
import com.ailetv.mobile.utils.extensions.toBitmap
import com.ailetv.mobile.utils.extensions.toast
import com.nmssalman.bubbleshowcasenew.BubbleShowCase
import com.nmssalman.bubbleshowcasenew.BubbleShowCaseBuilder
import com.nmssalman.bubbleshowcasenew.BubbleShowCaseListener
import getColorInt
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel


class BonusFragment : BaseFragment() {
    private val safeArgs by navArgs<BonusFragmentArgs>()
    override val viewModel by viewModel<BonusVM>()
    private val binding by lazy { FragmentBonusBinding.inflate(layoutInflater) }

    private val contractList by lazy { safeArgs.list.toList() }

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
        return binding.root
    }

    private fun init() {
        initToolbar(binding.toolbar)

        viewModel.setContractPOJO(contractList.getOrNull(0))
    }

    private fun setClicks() {
        binding.transferBtn.setOnClickListener {
            viewModel.transferBonus()
        }

        binding.infoBtn.setOnClickListener { viewModel.getBonusHint() }
        binding.transferToBtn.setOnClickListener { showTransferTo() }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contractPOJO.collectLatest {
                    binding.transferToBtn.text = it.toString()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transferSuccess.collectLatest {
                    toast(getString(R.string.toast_bonus_transfer_successfully))

                    EventBus.refreshMain.value = true
                    findNavController().navigateUp()
                }
            }
        }

        viewModel.balance.observe(viewLifecycleOwner) {
            showBonusBalance()
        }
    }

    private fun showCase() {
        var showing = true

        val case = BubbleShowCaseBuilder(requireActivity())
            .title(getString(R.string.showcase_bonus_hint))
            .backgroundColor(context.getColorInt(R.color.white))
            .textColor(context.getColorInt(R.color.black))
            .targetView(binding.infoBtn)
            .listener(object : BubbleShowCaseListener {
                override fun onBackgroundDimClick(bubbleShowCase: BubbleShowCase) {
                    showing = false
                    bubbleShowCase.dismiss()
                    viewModel.getBonusHint()
                }

                override fun onTargetClick(bubbleShowCase: BubbleShowCase) {
                    showing = false
                    viewModel.getBonusHint()
                }

                override fun onBubbleClick(bubbleShowCase: BubbleShowCase) {}
                override fun onCloseActionImageClick(bubbleShowCase: BubbleShowCase) {
                    showing = false
                }
            })
            .show()

        onBackPressedCallback {
            if (showing) {
                case.dismiss()

                showing = false
            } else
                findNavController().navigateUp()
        }

        SessionManager.showCaseBonusHint = false
    }

    private fun showBonusBalance() {
        binding.bonusImg.postDelayed({
            binding.bonusImg.setImageBitmap(binding.bonusLayout.toBitmap())

            binding.bonusTxt.isVisible = false
            binding.amountTxt.isVisible = false

            binding.motionLayout.transitionToEnd{
                if (SessionManager.showCaseBonusHint)
                    showCase()
            }
        }, 100)
    }

    private fun showTransferTo() {
        val list by lazy {
            contractList.map { it.toString() }
        }

        val dialog = ListDialog(context)
        dialog.show(list)

        dialog.onItemClick = { _, index ->
            viewModel.setContractPOJO(contractList.getOrNull(index))

            dialog.dismiss()
        }
    }

    override fun hideActionBar() = true
}