package com.ailetv.mobile.utils.extensions

import com.ailetv.mobile.manager.SessionManager
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

fun String?.toIntOrZero(): Int {
    return this?.toIntOrNull() ?: 0
}

fun String?.toDoubleOrZero(): Double {
    var text = this.trimSpace()

    if (text.endsWith("."))
        text = text.substring(0, text.length - 1)

    return text.toDoubleOrNull() ?: 0.0
}

fun String?.trimSpace(): String {
    return this?.replace(" ", "") ?: ""
}


fun String?.getFormattedDate(
    newFormat: String,
    currentFormat: String = "yyyy-MM-dd'T'HH:mm:ss"
): String {
    this?.let { date ->
        try {
            val fmt = SimpleDateFormat(currentFormat, Locale(SessionManager.language))
            val myFmt = SimpleDateFormat(newFormat, Locale(SessionManager.language))
            fmt.parse(date)?.let {
                return myFmt.format(it)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    return this ?: ""
}