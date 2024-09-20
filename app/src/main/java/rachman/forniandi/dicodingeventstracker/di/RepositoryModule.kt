package rachman.forniandi.dicodingeventstracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rachman.forniandi.dicodingeventstracker.data.remote.response.RemoteDataSource
import rachman.forniandi.dicodingeventstracker.data.remote.retrofit.EventRepositoryImpl
import rachman.forniandi.dicodingeventstracker.data.repository.FavoriteEventRepositoryImpl
import rachman.forniandi.dicodingeventstracker.data.repository.SettingThemeRepositoryImpl
import rachman.forniandi.dicodingeventstracker.domain.repository.EventsRepository
import rachman.forniandi.dicodingeventstracker.domain.repository.FavoriteEventRepository
import rachman.forniandi.dicodingeventstracker.domain.repository.SettingThemeRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideEventRepository(remoteDataSource: RemoteDataSource): EventsRepository {
        return EventRepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideFavoriteEventRepository(@ApplicationContext context: Context): FavoriteEventRepository {
        val localDataSource = DatabaseModule.provideLocalDataSource(context)
        return FavoriteEventRepositoryImpl(localDataSource)
    }


    @Provides
    fun provideSettingThemeEventRepository(@ApplicationContext context: Context): SettingThemeRepository {
        val localDataSource = DatabaseModule.provideLocalDataSource(context)
        return SettingThemeRepositoryImpl(localDataSource)
    }
}
