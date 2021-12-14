package com.ailetv.mobile.ui.auth.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ailetv.mobile.R
import com.ailetv.mobile.databinding.FragmentOtpBinding
import com.ailetv.mobile.service.MyFirebaseMessagingService
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.findNavControllerRoot
import com.google.firebase.messaging.FirebaseMessaging
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class OtpFragment : BaseFragment() {
    private val safeArgs by navArgs<OtpFragmentArgs>()
    override val viewModel: OtpVM by viewModel {
        parametersOf(safeArgs.utilModel)
    }
    private val binding by lazy { FragmentOtpBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        init()
        initObservers()
        return binding.root
    }

    private fun init() {
        KeyboardVisibilityEvent.registerEventListener(activity) {
            if (it)
                binding.motionLayout.transitionToEnd()
            else
                binding.motionLayout.transitionToStart()
        }
    }

    private fun initObservers() {
        viewModel.navigateToNext.observe(viewLifecycleOwner) {
            val context = context?.applicationContext

            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful)
                    MyFirebaseMessagingService.sendRegistrationToServer(context, task.result)
            }

            findNavControllerRoot()?.navigate(R.id.action_global_mainFragment)
        }
    }
}