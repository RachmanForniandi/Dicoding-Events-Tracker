package rachman.forniandi.dicodingeventstracker.domain.repository

import androidx.lifecycle.LiveData
import rachman.forniandi.dicodingeventstracker.domain.entity.Events

interface FavoriteEventRepository {

    suspend fun insertFavEvent(events: Events)

    suspend fun deleteFavEvent(events: Events)

    fun showFavEvent(): LiveData<List<Events>>

    //suspend fun deleteAllFavEvent()
}