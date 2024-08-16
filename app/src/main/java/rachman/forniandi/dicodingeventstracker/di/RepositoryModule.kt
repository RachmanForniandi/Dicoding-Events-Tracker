package rachman.forniandi.dicodingeventstracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rachman.forniandi.dicodingeventstracker.data.remote.retrofit.EventRepositoryImpl
import rachman.forniandi.dicodingeventstracker.domain.EventsRepository
import javax.inject.Singleton

/*
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRepository(repository: EventRepositoryImpl):EventsRepository

}*/
