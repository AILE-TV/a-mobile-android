package com.ailetv.mobile.ui.dashboard.myAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ailetv.mobile.R
import com.ailetv.mobile.data.model.resource.ButtonModel
import com.ailetv.mobile.data.model.resource.ErrorDialogModel
import com.ailetv.mobile.databinding.FragmentMyAccountBinding
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.*

class MyAccountFragment : BaseFragment() {
    override val viewModel by viewModel<MyAccountVM>()
    private val binding by lazy { FragmentMyAccountBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        init()
        return binding.root
    }

    private fun init() {
        val adapter = MyAccountAdapter()
        binding.recyclerView.adapter = adapter

        adapter.onContactUsClick = { context.intentCall(it) }
        adapter.onLiveTvClick = { context.intentLiveTv() }
        adapter.onWhatsappClick = { context.intentWhatsapp() }
        adapter.onFacebookClick = { context.intentFacebook() }
        adapter.onInstagramClick = { context.intentInstagram() }

        adapter.onLogoutClick = {
            val positiveButton = ButtonModel(R.string.yes) {
                viewModel.logout()
            }

            showErrorDialog(
                ErrorDialogModel(
                    title = getString(R.string.are_you_sure),
                    negativeButton = ButtonModel(R.string.no),
                    positiveButton = positiveButton
                )
            )
        }
    }

    override fun hideActionBar() = true
}