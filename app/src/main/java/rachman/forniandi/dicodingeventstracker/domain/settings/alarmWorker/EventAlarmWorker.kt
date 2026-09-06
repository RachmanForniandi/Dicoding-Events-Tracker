package rachman.forniandi.dicodingeventstracker.domain.settings.alarmWorker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.data.remote.retrofit.NetworkService
import rachman.forniandi.dicodingeventstracker.domain.detail.DetailEventsActivity

/**
 * [EventAlarmWorker] fetches the latest finished event from the Dicoding Events API and
 * posts a notification to the user.
 *
 * Uses [HiltWorker] + [AssistedInject] so that [NetworkService] (and any other Hilt-managed
 * dependency) can be injected directly — no manual OkHttp/Moshi construction needed.
 *
 * WorkManager retries this worker with exponential back-off on transient network failures.
 */
@HiltWorker
class EventAlarmWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val networkService: NetworkService,
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "EventAlarmWorker"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "event_reminder_channel"
        private const val CHANNEL_NAME = "Event Alarm Reminder"

        // active = -1 → finished events; limit = 1 → most recent single event
        private const val ACTIVE_FINISHED = -1
    }

    override suspend fun doWork(): Result {
        return try {
            val response = networkService.getEvents(active = ACTIVE_FINISHED)

            if (!response.isSuccessful) {
                Log.w(TAG, "API error ${response.code()} — will retry")
                return Result.retry()
            }

            val body = response.body()
                ?: run {
                    Log.w(TAG, "Empty response body — will retry")
                    return Result.retry()
                }

            val event = body.listEvents.firstOrNull()
                ?: run {
                    Log.i(TAG, "No events returned — nothing to notify")
                    return Result.success()
                }

            val pendingIntent = buildDetailPendingIntent(event.id)

            showNotification(
                title = context.getString(R.string.upcoming_event_title),
                description = context.getString(
                    R.string.don_t_miss_event_on,
                    event.name,
                    event.beginTime
                ),
                pendingIntent = pendingIntent
            )

            Log.i(TAG, "Notification posted for event: ${event.name}")
            Result.success()

        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error in EventAlarmWorker", e)
            Result.retry()
        }
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private fun buildDetailPendingIntent(eventId: Int?): PendingIntent {
        val intent = Intent(context, DetailEventsActivity::class.java).apply {
            putExtra(DetailEventsActivity.EXTRA_EVENT_ID, eventId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun showNotification(
        title: String,
        description: String,
        pendingIntent: PendingIntent
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableVibration(true)
            enableLights(true)
        }
        notificationManager.createNotificationChannel(channel)

        val bitmap = context.vectorToBitmap(R.drawable.ic_icon_notif)
        val bigPictureStyle = NotificationCompat.BigPictureStyle().bigPicture(bitmap)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_icon_notif)
            .setLargeIcon(bitmap)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setStyle(bigPictureStyle)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun Context.vectorToBitmap(drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(this, drawableId) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        ) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}