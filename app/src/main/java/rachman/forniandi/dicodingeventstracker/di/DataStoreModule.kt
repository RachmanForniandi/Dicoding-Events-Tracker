package rachman.forniandi.dicodingeventstracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rachman.forniandi.dicodingeventstracker.data.local.LocalDataSource
import rachman.forniandi.dicodingeventstracker.data.local.datastore.SettingThemePreferences
import rachman.forniandi.dicodingeventstracker.data.local.datastore.SettingThemePreferencesImpl
import rachman.forniandi.dicodingeventstracker.data.repository.SettingThemeRepositoryImpl
import rachman.forniandi.dicodingeventstracker.domain.repository.SettingThemeRepository
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun provideSettingThemePreferences(context: Context):SettingThemePreferences{
        return SettingThemePreferencesImpl.getInstance(context)
    }

    /*@Provides
    fun provideSettingThemeRepository(localDataSource: LocalDataSource):SettingThemeRepository{
        return SettingThemeRepositoryImpl.getInstanceForSettingTheme(localDataSource)
    }*/

}