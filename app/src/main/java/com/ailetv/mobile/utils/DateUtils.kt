package com.ailetv.mobile.utils

import android.annotation.SuppressLint
import com.ailetv.mobile.manager.SessionManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun calendarToDate(calendar: Calendar, newPattern: String?): String? {
    var date: String? =
        calendar[Calendar.YEAR].toString() + "-" +
                (calendar[Calendar.MONTH] + 1) + "-" +
                calendar[Calendar.DAY_OF_MONTH] + " " +
                calendar[Calendar.HOUR_OF_DAY] + ":" +
                calendar[Calendar.MINUTE]

    date = try {
        val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val myFmt = SimpleDateFormat(newPattern, Locale(SessionManager.language))

        fmt.parse(date ?: "")?.let {
            return myFmt.format(it)
        }
    } catch (e: ParseException) {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(Calendar.getInstance().time)
    }
    return date
}