package com.ailetv.mobile.utils.bindingAdapters

import androidx.databinding.BindingAdapter
import com.ailetv.mobile.data.enums.UiState
import com.ailetv.mobile.widget.StateView
import com.ailetv.mobile.widget.ViewState


@BindingAdapter(value = ["app:sv_viewState", "app:isSwipeRefresh"], requireAll = false)
fun StateView.viewState(uiState: UiState?, isSwipeRefresh: Boolean = false) {
    setViewState(
        when (uiState) {
            UiState.SUCCESS -> ViewState.CONTENT
            UiState.LOADING -> if (isSwipeRefresh) getViewState() else ViewState.LOADING
            UiState.ERROR -> ViewState.ERROR
            UiState.EMPTY -> ViewState.EMPTY
            else -> ViewState.CONTENT
        }
    )
}