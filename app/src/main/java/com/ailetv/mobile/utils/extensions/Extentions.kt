
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.ailetv.mobile.utils.extensions.toDoubleOrZero
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject


fun Int.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )
        .toInt()
}

fun Float.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
        .toInt()
}

fun Context?.getString(@StringRes resId: Int): String {
    return getString(resId)
}

fun Context?.getColorInt(@ColorRes resId: Int): Int {
    this?.let {
        return ContextCompat.getColor(it, resId)
    }
    return Color.BLACK
}

fun Context?.getColorStateListByAttr(@AttrRes resId: Int): ColorStateList {
    return ColorStateList.valueOf(this.getColorAttrs(resId))
}

fun Context?.getColorAttrs(@AttrRes resId: Int): Int {
    val typedValue = TypedValue()
    this?.theme?.resolveAttribute(resId, typedValue, true)

    return typedValue.data
}

fun Drawable?.setColorFilter(@ColorInt color: Int) {
    this?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}

fun Context?.isNetworkAvailable(): Boolean {
    this?.let {
        val connectivityManager =
            it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    return true
}

fun Context?.toast(message: Any?) {
    this?.let {
        val toast = Toast.makeText(it, message.toString(), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}

fun Context?.isNightMode(): Boolean {
    return getNightMode() == Configuration.UI_MODE_NIGHT_YES
}

fun Context?.getNightMode(): Int {
    return this?.let {
        resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    } ?: Configuration.UI_MODE_NIGHT_NO
}


fun Context?.share(text: String?) {
    this?.let {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        it.startActivity(sendIntent)
    }
}

fun locationOf(lat: Any?, lng: Any?): Location {
    val location = Location("")
    location.latitude = lat.toString().toDoubleOrZero()
    location.longitude = lng.toString().toDoubleOrZero()
    return location
}

inline fun <reified T> Bundle?.toJson(): T? {
    return this?.keySet()?.let {
        val json = JSONObject()
        for (key in it) {
            try {
                json.put(key, JSONObject.wrap(get(key)))
            } catch (e: JSONException) {
            }
        }

        Gson().fromJson(json.toString(), T::class.java)
    }
}

fun Map<String, String>.toBundle(): Bundle {
    val bundle = Bundle()
    for ((key, value) in this) {
        bundle.putString(key, value)
    }

    return bundle
}


fun countDownTimer(millisInFuture: Long, onFinish: () -> Unit) {
    object : CountDownTimer(millisInFuture, 1000L) {
        override fun onTick(p0: Long) {}

        override fun onFinish() {
            onFinish()
        }
    }.start()
}