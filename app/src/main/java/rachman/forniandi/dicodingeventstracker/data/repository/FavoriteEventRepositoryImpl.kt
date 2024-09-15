package rachman.forniandi.dicodingeventstracker.data.repository

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import rachman.forniandi.dicodingeventstracker.domain.repository.FavoriteEventRepository
import rachman.forniandi.dicodingeventstracker.data.local.LocalDataSource
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.DataMapper
import rachman.forniandi.dicodingeventstracker.domain.entity.Events

class FavoriteEventRepositoryImpl(private val localDataSource: LocalDataSource
): FavoriteEventRepository {
    override suspend fun insertFavEvent(events: Events) {
        val entity = DataMapper.mapEventDomainToEntity(events)
        localDataSource.insertFavoriteEvent(entity)
    }

    override suspend fun deleteFavEvent(events: Events) {
        val entity = DataMapper.mapEventDomainToEntity(events)
        localDataSource.deleteFavoriteEvent(entity)
    }

    override fun showFavEvent()= liveData {
        val localEvent = localDataSource.showFavoriteEvent().map { localEvent ->
            localEvent.map { eventEntity ->
                DataMapper.mapEventEntityToDomain(eventEntity)
            }
        }
        emitSource(localEvent)
    }
}