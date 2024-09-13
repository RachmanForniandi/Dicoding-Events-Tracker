package rachman.forniandi.dicodingeventstracker.data.local

import rachman.forniandi.dicodingeventstracker.data.local.datastore.SettingThemePreferences
import rachman.forniandi.dicodingeventstracker.data.remote.retrofit.NetworkService
import rachman.forniandi.dicodingeventstracker.data.room.EventDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val settingThemePreferences: SettingThemePreferences,
    private val eventDao: EventDao
){
    fun getTheme()= settingThemePreferences.getTheme()

    suspend fun setTheme(isDarkModeThemeActive:Boolean)= settingThemePreferences.setTheme(isDarkModeThemeActive)

    suspend fun insertFavoriteEvent(eventEntity: EventEntity) = eventDao.insertFavoriteEvent(eventEntity)

    fun showFavoriteEvent()= eventDao.readFavoriteEvents()

    suspend fun deleteFavoriteEvent(eventEntity: EventEntity)= eventDao.deleteFavoriteEvent(eventEntity)

    suspend fun deleteAllFavoriteEvent()= eventDao.deleteAllFavoriteEvents()
}