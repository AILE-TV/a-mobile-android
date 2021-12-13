package com.ailetv.mobile.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ailetv.mobile.databinding.FragmentAuthBinding
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.sharedElementEnterTransition


class AuthFragment : BaseFragment() {
    private val binding by lazy { FragmentAuthBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init()
        return binding.root
    }

    private fun init() {
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(
                AuthFragmentDirections.actionToLoginFragment()
            )
        }
    }

    override fun isLightStatusBarText() = false
    override fun hideActionBar() = true
}