package com.ailetv.mobile.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ailetv.mobile.R
import com.ailetv.mobile.databinding.ActivityMainBinding
import com.ailetv.mobile.ui.base.BaseActivity
import com.ailetv.mobile.utils.KeyboardEventListener
import com.ailetv.mobile.utils.StatusBarUtil
import getColorInt
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import setColorFilter

class MainActivity : BaseActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController
    private var lastDestinationId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTranslucent(this)
        setContentView(binding.root)

        initEventBus()
        initNavController()

        KeyboardVisibilityEvent.registerEventListener(this) {
            if(!it)
                currentFocus?.clearFocus()
        }

        KeyboardEventListener(binding.root, this)
    }

    private fun initEventBus() {
        EventBus.navigateToMain.observe(this) {
            navController.navigate(R.id.action_global_mainFragment)
        }

        EventBus.navigateToSplash.observe(this) {
            navController.navigate(R.id.action_global_splash)
        }
    }

    private fun initNavController() {
        setSupportActionBar(binding.toolbar)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_root) as NavHost
        navController = navHost.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination !is FloatingWindow) {
                if (lastDestinationId == R.id.mainFragment)
                    setupWithNavController(navController)

                lastDestinationId = destination.id
            }
        }
        setupWithNavController(navController)
    }

    fun setupWithNavController(
        navController: NavController,
        appBarConfiguration: AppBarConfiguration = AppBarConfiguration(setOf())
    ) {
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    fun changeWindowBackground(drawable: Drawable?, showHalfOval: Boolean) {
        binding.root.background = drawable
        binding.halfOvalImg.isVisible = showHalfOval
    }

    fun changeActionBarBackground(drawable: Drawable?) {
        binding.toolbar.background = drawable
    }

    fun changeToolbarTextColor(isLightText: Boolean) {
        val color = if (isLightText) getColorInt(R.color.white) else getColorInt(R.color.black)

        binding.toolbar.setTitleTextColor(color)
        binding.toolbar.navigationIcon?.setColorFilter(color)
    }

    fun getToolbar() = binding.toolbar
}