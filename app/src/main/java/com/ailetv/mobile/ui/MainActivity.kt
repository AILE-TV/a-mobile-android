package com.ailetv.mobile.ui

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import com.google.firebase.FirebaseApp
import getColorInt
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import setColorFilter

class MainActivity : BaseActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController
    private var lastDestinationId = 0

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(this, "İcazə verilmədi. Bildiriş göstərilməyəcək.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        FirebaseApp.initializeApp(this)
        StatusBarUtil.setTranslucent(this)
        setContentView(binding.root)

        initEventBus()
        initNavController()

        KeyboardVisibilityEvent.registerEventListener(this) {
            if (!it)
                currentFocus?.clearFocus()
        }

        KeyboardEventListener(binding.root, this)
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {}
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    AlertDialog.Builder(this)
                        .setTitle("Bildirişlər üçün icazə tələb olunur")
                        .setMessage("Əhəmiyyətli yeniliklər barədə sizə bildiriş göndərə bilmək üçün icazəyə ehtiyacımız var.")
                        .setPositiveButton("OK") { _, _ ->
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        .setNegativeButton("İmtina et") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
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