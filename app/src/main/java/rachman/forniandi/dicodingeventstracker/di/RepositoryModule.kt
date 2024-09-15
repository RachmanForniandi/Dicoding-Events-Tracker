package rachman.forniandi.dicodingeventstracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rachman.forniandi.dicodingeventstracker.data.local.LocalDataSource
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
    fun provideFavoriteEventRepository(localDataSource: LocalDataSource): FavoriteEventRepository {
        return FavoriteEventRepositoryImpl(localDataSource)
    }


    @Provides
    fun provideSettingThemeEventRepository(localDataSource: LocalDataSource): SettingThemeRepository {
        return SettingThemeRepositoryImpl(localDataSource)
    }
}
