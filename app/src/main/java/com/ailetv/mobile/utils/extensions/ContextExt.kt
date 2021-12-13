package com.ailetv.mobile.utils.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.ailetv.mobile.BuildConfig
import com.ailetv.mobile.manager.Constants

fun Context?.intentCall(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    this?.startActivity(intent)
}

fun Context?.intentPlayStore() {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data =
            Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
        setPackage("com.android.vending")
    }
    this?.startActivity(intent)
}


fun Context?.intentLiveTv() {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://play.google.com/store/apps/details?id=com.ailetv.plugin")
        setPackage("com.android.vending")
    }
    this?.startActivity(intent)
}

fun Context?.intentFacebook() {
    var uri: Uri = Uri.parse(Constants.URL_FACEBOOK)
    try {
        this?.let {
            val applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0)

            if (applicationInfo.enabled)
                uri = Uri.parse("fb://page/123833870968500")

            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    } catch (ignored: PackageManager.NameNotFoundException) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this?.startActivity(intent)
    }
}

fun Context?.intentInstagram() {
    val uri: Uri = Uri.parse(Constants.URL_INSTAGRAM)
    try {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.instagram.android")
        this?.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this?.startActivity(intent)
        } catch (t: Exception) {
        }
    }
}

fun Context?.intentWhatsapp(phoneNumber: String? = Constants.WHATSAPP_NUMBER) {
    val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    this?.startActivity(i)
}