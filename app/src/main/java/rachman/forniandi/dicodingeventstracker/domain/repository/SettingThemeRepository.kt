package rachman.forniandi.dicodingeventstracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingThemeRepository {
    fun getTheme(): Flow<Boolean>

    suspend fun setTheme(isDarkModeThemeActive:Boolean)

    fun getNotificationAlarm():Flow<Boolean>

    suspend fun setNotificationAlarm(isNotificationActive:Boolean)
}