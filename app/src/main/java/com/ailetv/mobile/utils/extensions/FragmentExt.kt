package com.ailetv.mobile.utils.extensions

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.transition.TransitionInflater
import com.ailetv.mobile.R
import com.google.android.material.appbar.MaterialToolbar
import toast

fun Fragment.appCompatActivity(): AppCompatActivity {
    return activity as AppCompatActivity
}

fun Fragment.supportActionBar(): ActionBar? {
    return appCompatActivity().supportActionBar
}

fun Fragment.findNavControllerRoot(): NavController? {
    return activity?.findNavController(R.id.nav_host_root)
}

fun Fragment.onBackPressedCallback(onBackPressed: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })
}

fun Fragment.initToolbar(toolbar: MaterialToolbar) {
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
}

fun Fragment.toast(message: Any?) {
    context?.toast(message)
}

fun Fragment.sharedElementEnterTransition() {
    sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
}