package rachman.forniandi.dicodingeventstracker.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rachman.forniandi.dicodingeventstracker.utils.ConstantsMain


private val Context.dataStoreCore by preferencesDataStore(name = ConstantsMain.SETTING_THEME_PREFERENCES)
class SettingThemePreferencesImpl (context: Context) : SettingThemePreferences{

    private val dataStore = context.dataStoreCore

    override fun getTheme(): Flow<Boolean> {
       return dataStore.data.map {
            it[ConstantsMain.THEME_DARK_MODE]?: false
        }
    }

    override suspend fun setTheme(isDarkModeThemeActive: Boolean) {
        dataStore.edit {
            it[ConstantsMain.THEME_DARK_MODE] = isDarkModeThemeActive
        }
    }

    companion object{
        @Volatile
        private var instance: SettingThemePreferencesImpl? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: SettingThemePreferencesImpl(context)
            }.also {
                instance = it
            }
    }
}