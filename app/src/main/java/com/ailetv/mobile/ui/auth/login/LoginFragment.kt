package com.ailetv.mobile.ui.auth.login

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.ailetv.mobile.databinding.FragmentLoginBinding
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.trimSpace
import com.ailetv.mobile.utils.hideSoftKeyboard
import com.ailetv.mobile.utils.showSoftKeyboard
import com.hbb20.CountryCodePicker
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment() {
    override val viewModel: LoginVM by viewModel()
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

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
        binding.phoneEdt.setOnFocusChangeListener { _, b ->
            if (b)
                binding.motionLayout.transitionToEnd()
            else
                binding.motionLayout.transitionToStart()
        }


        binding.countryCode.textView_selectedCountry.doAfterTextChanged {
            viewModel.countryCode.value = it.toString()
        }
        binding.countryCode.textView_selectedCountry.text =
            binding.countryCode.textView_selectedCountry.text

        binding.countryCode.setDialogEventsListener(object :
            CountryCodePicker.DialogEventsListener {
            override fun onCcpDialogOpen(dialog: Dialog?) {
                hideSoftKeyboard()
            }

            override fun onCcpDialogDismiss(dialogInterface: DialogInterface?) {
                binding.phoneTil.showSoftKeyboard()
            }

            override fun onCcpDialogCancel(dialogInterface: DialogInterface?) {
                binding.phoneTil.showSoftKeyboard()
            }
        })

        onCountryChange()
        binding.countryCode.setOnCountryChangeListener {
            onCountryChange()
        }

        binding.phoneEdt.doAfterTextChanged {
            binding.registerBtn.isEnabled =
                if (isAzerbaijan())
                    it.toString().trimSpace().length == 9
                else true
        }
        binding.countryCode.registerCarrierNumberEditText(binding.phoneEdt)
    }

    private fun onCountryChange() {
        binding.phoneEdt.filters = arrayOf(
            InputFilter.LengthFilter(if (isAzerbaijan()) 12 else 18)
        )
    }

    private fun isAzerbaijan() = binding.countryCode.selectedCountryCode == "994"

    private fun initObservers() {
        viewModel.navigateToNext.observe(viewLifecycleOwner) {
            findNavController().navigate(
                LoginFragmentDirections.actionToOtpFragment(it)
            )
        }
    }
}