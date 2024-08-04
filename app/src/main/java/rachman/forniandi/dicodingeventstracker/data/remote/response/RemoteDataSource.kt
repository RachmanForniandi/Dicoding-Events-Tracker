package rachman.forniandi.dicodingeventstracker.data.remote.response

import rachman.forniandi.dicodingeventstracker.data.remote.retrofit.NetworkService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkService: NetworkService
){
    suspend fun getFutureEvents(active:Int) = networkService.getEvents(active)

    suspend fun getPastEvents(active:Int) = networkService.getEvents(active)

    suspend fun getDetailEvents(idEvent:Int) = networkService.getDetailEvents(idEvent)

    suspend fun getSearchQueryEvent(active: Int,query:String) = networkService.searchEvents(active,query)
}