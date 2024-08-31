package rachman.forniandi.dicodingeventstracker.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILE_DATE_FORMAT = "dd-MMMM-yyyy HH:mm"

@SuppressLint("SimpleDateFormat")
fun getStringDate(date: String?): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val outputDate = SimpleDateFormat(FILE_DATE_FORMAT)
    var d: Date? = null
    try {
        d = dateFormat.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return outputDate.format(d)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.animateLoadingProcessData(isVisible: Boolean, duration: Long = 300) {
    ObjectAnimator
        .ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f)
        .setDuration(duration)
        .start()
}