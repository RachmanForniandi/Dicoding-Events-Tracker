package rachman.forniandi.dicodingeventstracker.domain.settings.alarmWorker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.data.remote.response.ResponseEvents
import rachman.forniandi.dicodingeventstracker.domain.detail.DetailEventsActivity
import java.io.IOException

class EventAlarmWorker (
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params){

    private var pendingIntent:PendingIntent?=null

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "event_reminder_channel"
        private const val CHANNEL_NAME = "Event Alarm Reminder"
        private const val API_URL = "https://event-api.dicoding.dev/events?active=-1&limit=1"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(API_URL)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful){
                    showErrorToast("Error: Unexpected response ${response.code}")
                    throw IOException("Unexpected response ${response.code}")
                }

                val responseBody = response.body?.string() ?: throw IOException("Empty response body")
                val moshi = Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
                val jsonAdapter = moshi.adapter(ResponseEvents::class.java)
                val eventResponse = jsonAdapter.fromJson(responseBody)

                eventResponse?.let { response ->
                    response.listEvents.firstOrNull()?.let { event ->
                        val toDetailAlarm = Intent(applicationContext,DetailEventsActivity::class.java).apply {
                            putExtra(DetailEventsActivity.EXTRA_EVENT_ID, event?.id)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        pendingIntent = PendingIntent.getActivity(
                            applicationContext,0,
                            toDetailAlarm,
                            PendingIntent.FLAG_IMMUTABLE)
                        showNotification(
                            title = "Upcoming Event!",
                            description = applicationContext.getString(
                                R.string.don_t_miss_event_on,
                                event.name,
                                event.beginTime
                            )
                        )
                        Result.success()
                    } ?: Result.failure()
                } ?: Result.failure()
            }
        }catch (e: Exception) {
            showErrorToast("Error: ${e.message}")
            Result.failure()
        }
    }

    private suspend fun showErrorToast(message: String) = withContext(Dispatchers.Main) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    private fun showNotification(title: String, description: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableVibration(true)
            enableLights(true)
        }
        val bitmap = applicationContext.vectorToBitmap(R.drawable.ic_icon_notif)
        val bigPicture= NotificationCompat.BigPictureStyle()
            .bigPicture(bitmap)


        notificationManager.createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_icon_notif)
            .setLargeIcon(bitmap)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setStyle(bigPicture)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)


    }
    private fun Context.vectorToBitmap(drawableId:Int): Bitmap?{
        val drawable = ContextCompat.getDrawable(this,drawableId) ?:return null
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)?:null
        val canvas = bitmap?.let { Canvas(it) }
        if (canvas != null) {
            drawable.setBounds(0,0, canvas.width,canvas.height)
        }
        if (canvas != null) {
            drawable.draw(canvas)
        }
        return bitmap
    }


}