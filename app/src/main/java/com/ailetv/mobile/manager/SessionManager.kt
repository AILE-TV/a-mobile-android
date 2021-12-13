package com.ailetv.mobile.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ailetv.mobile.ui.EventBus


object SessionManager {
    private const val KEY_TOKEN = "token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_PHONE_NUMBER = "phone_number"
    private const val KEY_LOGGED_IN = "logged_in"
    private const val KEY_LANGUAGE = "language"
    private const val KEY_SHOW_CASE_BONUS_HINT = "showcase_bonus_hint"

    private lateinit var sharedPref: SharedPreferences

    fun init(context: Context) {
        sharedPref =
            EncryptedSharedPreferences.create(
                context, "secret_shared_prefs",
                createMasterKey(context),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    var loggedIn
        get() = sharedPref.getBoolean(KEY_LOGGED_IN, false)
        set(value) {
            sharedPref.edit { putBoolean(KEY_LOGGED_IN, value) }
        }

    var token
        get() = sharedPref.getString(KEY_TOKEN, "") ?: ""
        set(value) {
            sharedPref.edit { putString(KEY_TOKEN, value) }
        }

    var phoneNumber
        get() = sharedPref.getString(KEY_PHONE_NUMBER, "") ?: ""
        set(value) {
            sharedPref.edit { putString(KEY_PHONE_NUMBER, value) }
        }

    var userId
        get() = sharedPref.getInt(KEY_USER_ID, 0)
        set(value) {
            sharedPref.edit { putInt(KEY_USER_ID, value) }
        }

    var language
        get() = sharedPref.getString(KEY_LANGUAGE, "en") ?: "en"
        set(value) {
            sharedPref.edit { putString(KEY_LANGUAGE, value) }
        }

    var showCaseBonusHint
        get() = sharedPref.getBoolean(KEY_SHOW_CASE_BONUS_HINT, true)
        set(value) {
            sharedPref.edit { putBoolean(KEY_SHOW_CASE_BONUS_HINT, value) }
        }

    fun setUserData(phoneNumber: String, userId: Int, token: String, loggedIn: Boolean) {
        sharedPref.edit {
            putString(KEY_PHONE_NUMBER, phoneNumber)
            putInt(KEY_USER_ID, userId)
            putString(KEY_TOKEN, token)
            putBoolean(KEY_LOGGED_IN, loggedIn)
        }
    }

    fun clearData(navigateToSplash: Boolean = true) {
        sharedPref.edit().clear().apply()

        if (navigateToSplash)
            EventBus.navigateToSplash.postValue(true)
    }

    private fun createMasterKey(context: Context) =
        MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
}