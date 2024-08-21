package rachman.forniandi.dicodingeventstracker.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILE_DATE_FORMAT = "dd-MMM-yyyy"
val timeStampImg: String = SimpleDateFormat(
    FILE_DATE_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

@SuppressLint("SimpleDateFormat")
fun getStringDate(date: String?): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputDate = SimpleDateFormat(FILE_DATE_FORMAT)
    var d: Date? = null
    try {
        d = dateFormat.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return outputDate.format(d)
}

fun View.animateLoadingProcessData(isVisible: Boolean, duration: Long = 300) {
    ObjectAnimator
        .ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f)
        .setDuration(duration)
        .start()
}