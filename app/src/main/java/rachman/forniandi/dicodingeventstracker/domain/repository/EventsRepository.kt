package rachman.forniandi.dicodingeventstracker.domain.repository

import androidx.lifecycle.LiveData
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.RemoteResponse
import rachman.forniandi.dicodingeventstracker.domain.entity.Events


interface EventsRepository {

    fun getFutureEvents(active:Int): LiveData<RemoteResponse<List<Events>?>?>

    fun getPastEvents(active:Int): LiveData<RemoteResponse<List<Events>?>?>

    fun getDetailEvents(id:Int): LiveData<RemoteResponse<Events?>?>

    fun searchEvents(active:Int,query:String):LiveData<RemoteResponse<List<Events>?>?>
}