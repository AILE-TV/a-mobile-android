package com.ailetv.mobile.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import java.net.NetworkInterface
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.regex.Pattern


object DeviceUtil {
    var deviceId: String? = ""
    var appVersion: String? = ""
    var appVersionFull: String? = ""

    fun init(context: Context) {
        initDeviceId(context)
        initAppVersion(context)
    }

    @SuppressLint("HardwareIds")
    private fun initDeviceId(context: Context?) {
        deviceId = encryptSHA256(
            Settings.Secure.getString(
                context?.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        )
    }

    private fun initAppVersion(context: Context) {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            appVersionFull = pInfo.versionName

            val v1 = pInfo.versionName?.split(Pattern.quote(".").toRegex())?.toTypedArray()
            appVersion = v1?.get(0) + "." + v1?.get(0)
        } catch (e: PackageManager.NameNotFoundException) {
        }
    }

    fun getIPAddress(useIPv4: Boolean): String {
        try {
            Collections.list(NetworkInterface.getNetworkInterfaces()).forEach { intf ->
                Collections.list(intf.inetAddresses).forEach { inet ->
                    if (!inet.isLoopbackAddress) {
                        val host: String = inet.hostAddress
                        val isIPv4 = host.indexOf(':') < 0

                        if (useIPv4) {
                            if (isIPv4) return host
                        } else {
                            if (!isIPv4) {
                                val index = host.indexOf('%') // drop ip6 zone suffix

                                return if (index < 0)
                                    host.uppercase()
                                else
                                    host.substring(0, index).uppercase()
                            }
                        }
                    }
                }
            }
        } catch (ignored: Exception) {
        }
        return "0.0.0.0"
    }

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL

        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }

        var capitalizeNext = true

        val phrase = StringBuilder()
        val arr = str.toCharArray()

        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }
        return phrase.toString()
    }


    private fun encryptSHA256(string: String): String? {
        val digest: MessageDigest
        var hash: String? = ""
        try {
            digest = MessageDigest.getInstance("SHA-256")
            digest.update(string.toByteArray())
            hash = bytesToHexString(digest.digest())
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
        }
        return hash
    }

    private fun bytesToHexString(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (aByte in bytes) {
            val hex = Integer.toHexString(0xFF and aByte.toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }
}