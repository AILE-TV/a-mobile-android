package com.ailetv.mobile.ui.splash

import android.os.Bundle
import android.se.omapi.Session
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.ailetv.mobile.R
import com.ailetv.mobile.data.enums.PushNotificationType
import com.ailetv.mobile.data.enums.PushNotificationType.*
import com.ailetv.mobile.data.model.resource.ButtonModel
import com.ailetv.mobile.data.model.resource.ErrorDialogModel
import com.ailetv.mobile.data.model.resource.PushNotificationModel
import com.ailetv.mobile.databinding.FragmentSplashBinding
import com.ailetv.mobile.manager.SessionManager
import com.ailetv.mobile.ui.EventBus
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.intentPlayStore
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import toJson


class Splash : BaseFragment() {
    override val viewModel: SplashVM by viewModel()
    private val binding by lazy { FragmentSplashBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        initObservables()
        checkNotificationData()
        return binding.root
    }

    private fun initObservables() {
        viewModel.hasForceUpdate
            .asLiveData()
            .observe(viewLifecycleOwner) { forceUpdate ->
                if (forceUpdate) {
                    val model = ErrorDialogModel(
                        title = getString(R.string.app_update_dialog_title),
                        message = getString(R.string.app_update_dialog_message),
                        cancelable = false,
                        positiveButton = ButtonModel { context.intentPlayStore() }
                    )
                    showErrorDialog(model)
                }
            }

        viewModel.isUserLoggedIn
            .asLiveData()
            .observe(viewLifecycleOwner) {
                val navArgs =
                    if (it)
                        SplashDirections.actionGlobalMainFragment()
                    else
                        SplashDirections.actionGlobalAuth()

                findNavController().navigate(
                    navArgs,
                    FragmentNavigatorExtras(binding.logoImg to "logoImg")
                )
            }
    }

    private fun checkNotificationData() {
        Timber.e(requireActivity().intent.getStringExtra("type"))
        requireActivity().intent.extras
            .toJson<PushNotificationModel>()?.let {
                requireActivity().intent.removeExtra("type")

                when (it.type) {
                    PUSH ->
                        EventBus.pushNotificationModel.value = it
                    else -> {

                    }
                }
            }
    }

    override fun isLightStatusBarText() = false
    override fun hideActionBar() = true
}