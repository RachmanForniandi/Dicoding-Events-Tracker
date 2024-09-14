package rachman.forniandi.dicodingeventstracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rachman.forniandi.dicodingeventstracker.data.local.LocalDataSource
import rachman.forniandi.dicodingeventstracker.data.local.datastore.SettingThemePreferences
import rachman.forniandi.dicodingeventstracker.data.room.EventDao
import rachman.forniandi.dicodingeventstracker.data.room.EventDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideEventDao(context: Context):EventDao{
        val eventDatabase = EventDatabase.getInstance(context)
        return eventDatabase.getEventDao()
    }

    @Provides
    fun provideLocalDataSource(settingThemePreferences: SettingThemePreferences,eventDao: EventDao):LocalDataSource{
        return LocalDataSource(settingThemePreferences, eventDao)
    }

}