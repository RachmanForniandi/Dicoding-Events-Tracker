package rachman.forniandi.dicodingeventstracker.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

class ConstantsMain {
    companion object {
        const val BASE_URL = "https://event-api.dicoding.dev/"
        val THEME_DARK_MODE = booleanPreferencesKey("is_dark_mode_theme_active")
        const val SETTING_THEME_PREFERENCES = "setting_theme_preferences"
    }
}