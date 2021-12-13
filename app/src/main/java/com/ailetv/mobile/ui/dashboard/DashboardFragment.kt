package com.ailetv.mobile.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ailetv.mobile.R
import com.ailetv.mobile.databinding.FragmentDashboardBinding
import com.ailetv.mobile.ui.EventBus
import com.ailetv.mobile.ui.MainActivity
import com.ailetv.mobile.ui.base.BaseFragment
import com.ailetv.mobile.utils.extensions.supportActionBar
import com.ailetv.mobile.utils.hide
import com.ailetv.mobile.utils.show
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent


class DashboardFragment : Fragment() {
    private val binding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }
    private var hideBottomNav = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setupBottomNavigationBar()
    }

    private fun init() {
        KeyboardVisibilityEvent.registerEventListener(activity) {
            if (it)
                binding.bottomNav.hide()
            else if (!hideBottomNav) {
                lifecycleScope.launch {
                    delay(50)

                    binding.bottomNav.isVisible = true
                }
            }
        }
    }

    private fun setupBottomNavigationBar() {
        val navController = findChildNavController()
        navController.addOnDestinationChangedListener(addOnDestinationChangedListener)

        val activity = activity as MainActivity
        activity.setupWithNavController(
            navController,
            AppBarConfiguration(
                setOf(
                    R.id.mainFragment, R.id.companiesFragment, R.id.servicesFragment,
                    R.id.notificationsFragment, R.id.myAccountFragment
                )
            )
        )

        binding.bottomNav.setupWithNavController(navController)

        EventBus.pushNotificationModel.value?.let {
            EventBus.pushNotificationModel.value = null

            binding.bottomNav.menu.performIdentifierAction(R.id.notificationsNavId, 0)
        }
    }

    private val addOnDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination !is FloatingWindow) {

                val fragment = primaryNavigationFragment()
                if (fragment is BaseFragment) {
                    if (!fragment.hideActionBar())
                        supportActionBar()?.show()
                    else
                        supportActionBar()?.hide()
                }

                hideBottomNav = when (destination.id) {
                    R.id.mainFragment,
                    R.id.companiesFragment,
                    R.id.servicesFragment,
                    R.id.notificationsFragment,
                    R.id.myAccountFragment -> false
                    R.id.webFragment -> true
                    else -> false
                }

                if (hideBottomNav) {
                    if (binding.bottomNav.top == 0)
                        binding.bottomNav.isVisible = false

                    binding.bottomNav.hide()
                } else {
                    binding.bottomNav.isVisible = true

                    binding.bottomNav.show()
                }
            }
        }


    private fun primaryNavigationFragment(): Fragment? {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        return navHostFragment.childFragmentManager.primaryNavigationFragment
    }

    private fun findChildNavController(): NavController {
        val navHost = childFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        return navHost.navController
    }
}