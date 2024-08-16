package rachman.forniandi.dicodingeventstracker.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rachman.forniandi.dicodingeventstracker.data.remote.response.RemoteDataSource
import rachman.forniandi.dicodingeventstracker.data.remote.retrofit.EventRepositoryImpl
import rachman.forniandi.dicodingeventstracker.data.remote.retrofit.NetworkService
import rachman.forniandi.dicodingeventstracker.domain.EventsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    fun provideRemoteDataSource(networkService: NetworkService):RemoteDataSource{
        return RemoteDataSource(networkService)
    }


    @Provides
    fun provideEventRepository(remoteDataSource: RemoteDataSource):EventsRepository{
        return EventRepositoryImpl(remoteDataSource)
    }
}