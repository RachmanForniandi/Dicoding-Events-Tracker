package rachman.forniandi.dicodingeventstracker.domain.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import rachman.forniandi.dicodingeventstracker.data.local.room.EventEntity
import rachman.forniandi.dicodingeventstracker.domain.entity.Events

interface FavoriteEventRepository {

    suspend fun insertFavEvent(eventEntity: EventEntity)

    suspend fun deleteFavEvent(eventEntity: EventEntity)

    fun showFavEvent(): Flow<List<EventEntity>>

    //suspend fun deleteAllFavEvent()
}