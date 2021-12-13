package com.ailetv.mobile.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.ailetv.mobile.R
import com.ailetv.mobile.widget.ViewState.Companion.findById

class StateView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private var viewState = ViewState.CONTENT
    private var loadingViewId = 0
    private var errorViewId = 0
    private var emptyViewId = 0
    private var refreshLayoutId = 0

    private var retryViewId = 0
    private var onRetryClickListener: OnClickListener? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed)
            layoutsCreated()
    }

    init {
        val t = context.obtainStyledAttributes(attrs, R.styleable.StateView)

        viewState = findById(t.getInt(R.styleable.StateView_sv_viewState, ViewState.CONTENT.id))
        loadingViewId = t.getResourceId(R.styleable.StateView_sv_loadingViewId, R.id.svLoadingView)
        errorViewId = t.getResourceId(R.styleable.StateView_sv_errorViewId, R.id.svErrorView)
        emptyViewId = t.getResourceId(R.styleable.StateView_sv_emptyViewId, R.id.svEmptyView)
        retryViewId = t.getResourceId(R.styleable.StateView_sv_retryViewId, R.id.svRetryBtn)
        refreshLayoutId =
            t.getResourceId(R.styleable.StateView_sv_refreshLayoutId, R.id.refreshLayout)

        t.recycle()
    }

    private fun layoutsCreated() {
        initContentGroup()

        setViewState(viewState)
    }

    private fun initContentGroup() {
        for (i in 0..childCount)
            getChildAt(i)?.let {
                if (it.id != loadingViewId || it.id != errorViewId || it.id != emptyViewId) {
                    if (it.id == -1)
                        it.id = View.generateViewId()
                }

                if (it.id == retryViewId)
                    it.setOnClickListener(onRetryClickListener)
            }
    }

    fun setViewState(viewState: ViewState) {
        this.viewState = viewState

        for (i in 0 until childCount) {
            getChildAt(i).apply {
                when (this.id) {
                    loadingViewId ->
                        this.isVisible = viewState == ViewState.LOADING
                    errorViewId ->
                        this.isVisible = viewState == ViewState.ERROR
                    emptyViewId ->
                        this.isVisible = viewState == ViewState.EMPTY
                    refreshLayoutId ->
                        this.isVisible =
                            viewState == ViewState.EMPTY || viewState == ViewState.CONTENT //show SwipeRefreshLayout when list is empty
                    else -> {
                        if (this.tag == null || this.isVisible)
                            this.tag = this.isVisible

                        this.isVisible = viewState == ViewState.CONTENT && this.tag == true
                    }
                }
            }
        }
    }

    fun getViewState() = viewState

    fun setOnRetryClick(listener: OnClickListener?) {
        onRetryClickListener = listener
    }
}

enum class ViewState(val id: Int) {
    CONTENT(0),
    LOADING(1),
    ERROR(2),
    EMPTY(3);

    companion object {
        fun findById(id: Int) = values().find { it.id == id } ?: CONTENT
    }
}