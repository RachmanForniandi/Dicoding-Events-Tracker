package rachman.forniandi.dicodingeventstracker.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

class ConstantsMain {
    companion object {
        const val BASE_URL = "https://event-api.dicoding.dev/"
        val THEME_DARK_MODE = booleanPreferencesKey("is_dark_mode_theme_active")
        const val SETTING_THEME_PREFERENCES = "setting_theme_preferences"
        val NOTIFICATION_KEY = booleanPreferencesKey("notification_setting")

        const val ALARM_ID_REPEATING = 100
        const val ALARM_CHANNEL_ID = "channel_reminder"
        const val ALARM_CHANNEL_NAME = "Reminder Channel"
        const val ALARM_TITTLE = "alarm_tittle"
        const val ALARM_MESSAGE = "alarm_message"
    }
}