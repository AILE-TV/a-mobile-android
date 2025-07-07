package com.ailetv.mobile.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import getColorAttrs
import com.ailetv.mobile.R
import com.ailetv.mobile.data.model.resource.ErrorDialogModel
import com.ailetv.mobile.ui.MainActivity
import com.ailetv.mobile.utils.StatusBarUtil.changeStatusBarMode
import com.ailetv.mobile.utils.dialogs.ErrorDialog
import com.ailetv.mobile.utils.dialogs.ProgressDialog
import com.ailetv.mobile.utils.extensions.appCompatActivity
import com.ailetv.mobile.utils.extensions.supportActionBar
import androidx.core.graphics.drawable.toDrawable


abstract class BaseFragment : Fragment() {
    private val _progressDialog = ProgressDialog(lifecycle)
    private val _errorDialog = ErrorDialog(lifecycle)

    private val progressDialog by lazy {
        return@lazy _progressDialog.createDialog(
            requireContext(),
            layoutInflater
        )
    }
    private val errorDialog by lazy {
        return@lazy _errorDialog.createDialog(
            requireContext(),
            layoutInflater
        )
    }

    protected open val viewModel: BaseViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.showErrorDialog
            ?.observe(viewLifecycleOwner) { errorDialog.show(context, it) }

        viewModel?.showProgressDialog
            ?.observe(viewLifecycleOwner) { progressDialog.setUiState(it) }

        initActionBar()
    }

    open fun showErrorDialog(model: ErrorDialogModel) {
        errorDialog.show(context, model)
    }

    private fun initActionBar() {
        if (parentFragment is NavHostFragment) {
            val mainActivity = appCompatActivity() as MainActivity
            mainActivity.changeWindowBackground(
                drawable = getWindowBackground(),
                showHalfOval = showHalfOval()
            )
            mainActivity.changeActionBarBackground(drawable = getActionBarBackground())
            mainActivity.changeToolbarTextColor(isLightText = isLightStatusBarText())

            changeStatusBarMode()

            if (hideActionBar())
                supportActionBar()?.hide()
            else
                supportActionBar()?.show()
        }
    }

    open fun showHalfOval() = true
    open fun hideActionBar() = false
    open fun isLightStatusBarText() = true
    open fun getActionBarBackground() = ColorDrawable(Color.TRANSPARENT)
    open fun getWindowBackground(): Drawable? =
        requireContext().getColor(R.color.main_color).toDrawable()
}