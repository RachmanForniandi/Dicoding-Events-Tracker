package rachman.forniandi.dicodingeventstracker.domain.repository

import kotlinx.coroutines.flow.Flow
import rachman.forniandi.dicodingeventstracker.data.local.room.EventEntity

interface FavoriteEventRepository {

    suspend fun insertFavEvent(eventEntity: EventEntity)

    suspend fun deleteFavEvent(eventEntity: EventEntity)

    fun showFavEvent(): Flow<List<EventEntity>>

    fun deleteAllFavEvent()
}