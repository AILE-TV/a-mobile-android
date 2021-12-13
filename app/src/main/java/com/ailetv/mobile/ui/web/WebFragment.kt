package com.ailetv.mobile.ui.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.databinding.FragmentWebBinding
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.initToolbar
import com.ailetv.mobile.utils.extensions.onBackPressedCallback
import isNetworkAvailable
import org.koin.androidx.viewmodel.ext.android.viewModel


class WebFragment : BaseFragment() {
    override val viewModel: WebVM by viewModel()
    private val safeArgs: WebFragmentArgs by navArgs()

    private val binding by lazy { FragmentWebBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        init()
        initObservers()
        initWebSettings()
        loadUrl()
        return binding.root
    }

    private fun init() {
        binding.toolbar.title = safeArgs.title
        initToolbar(binding.toolbar)

        onBackPressedCallback {
            if (safeArgs.webCanGoBack && binding.webView.canGoBack())
                binding.webView.goBack()
            else {
                findNavController().navigateUp()
            }
        }

        binding.webView.setMixedContentAllowed(false)
    }

    private fun initObservers() {
        viewModel.reloadPageLiveData
            .observe(viewLifecycleOwner) {
                loadUrl()
            }
    }

    private fun loadUrl() {
        safeArgs.url.let { url ->
            if (safeArgs.postData.isNullOrEmpty())
                binding.webView.loadUrl(url)
            else {
                val postData = safeArgs.postData

                binding.webView.postUrl(url, postData.toString().toByteArray())
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSettings() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = false

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                if (context.isNetworkAvailable())
                    viewModel.uiState.postValue(UiState.SUCCESS)
                else
                    viewModel.uiState.postValue(UiState.ERROR)
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                viewModel.uiState.postValue(UiState.LOADING)
            }

            override fun onReceivedError(
                view: WebView?, request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)

                viewModel.uiState.postValue(UiState.ERROR)
            }

            override fun onReceivedSslError(
                view: WebView, handler: SslErrorHandler,
                error: SslError
            ) {
                super.onReceivedSslError(view, handler, error)
                viewModel.uiState.postValue(UiState.ERROR)
            }
        }

        binding.webView.webChromeClient = WebChromeClient()
    }

    override fun onResume() {
        binding.webView.onResume()
        super.onResume()
    }

    override fun onPause() {
        binding.webView.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        binding.webView.onDestroy()
        super.onDestroyView()
    }

    override fun hideActionBar() = true
}