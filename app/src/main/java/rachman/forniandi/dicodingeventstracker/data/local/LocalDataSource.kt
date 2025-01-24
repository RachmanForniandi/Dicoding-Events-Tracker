package rachman.forniandi.dicodingeventstracker.data.local

import rachman.forniandi.dicodingeventstracker.data.local.datastore.SettingThemePreferences
import rachman.forniandi.dicodingeventstracker.data.local.room.EventDao
import rachman.forniandi.dicodingeventstracker.data.local.room.EventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val settingThemePreferences: SettingThemePreferences,
    private val eventDao: EventDao
){
    fun getTheme()= settingThemePreferences.getTheme()

    suspend fun setTheme(isDarkModeThemeActive:Boolean)= settingThemePreferences.setTheme(isDarkModeThemeActive)

    fun getNotificationAlarm():Flow<Boolean> = settingThemePreferences.getNotificationAlarm()

    suspend fun setNotificationAlarm(isNotificationActive:Boolean)= settingThemePreferences.setNotificationAlarm(isNotificationActive)

    suspend fun insertFavoriteEvent(eventEntity: EventEntity) = eventDao.insertFavoriteEvent(eventEntity)

    fun showFavoriteEvent(): Flow<List<EventEntity>> = eventDao.readFavoriteEvents()

    suspend fun deleteFavoriteEvent(eventEntity: EventEntity)= eventDao.deleteFavoriteEvent(eventEntity)

    fun deleteAllFavoriteEvent() = eventDao.deleteAllFavoriteEvents()
}