package rachman.forniandi.dicodingeventstracker.data.repository

import kotlinx.coroutines.flow.Flow
import rachman.forniandi.dicodingeventstracker.data.local.LocalDataSource
import rachman.forniandi.dicodingeventstracker.domain.repository.SettingThemeRepository

class SettingThemeRepositoryImpl(
    private val localDataSource: LocalDataSource
): SettingThemeRepository {

    override fun getTheme(): Flow<Boolean> {
        return localDataSource.getTheme()
    }

    override suspend fun setTheme(isDarkModeThemeActive: Boolean) {
        return localDataSource.setTheme(isDarkModeThemeActive)
    }

    override fun getNotificationAlarm(): Flow<Boolean> {
        return localDataSource.getNotificationAlarm()
    }

    override suspend fun setNotificationAlarm(isNotificationActive: Boolean) {
        return localDataSource.setNotificationAlarm(isNotificationActive)
    }

}