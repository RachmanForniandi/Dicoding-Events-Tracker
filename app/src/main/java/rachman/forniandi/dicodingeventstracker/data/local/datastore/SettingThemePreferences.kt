package rachman.forniandi.dicodingeventstracker.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface SettingThemePreferences {
    fun getTheme():Flow<Boolean>

    suspend fun setTheme(isDarkModeThemeActive:Boolean)
}