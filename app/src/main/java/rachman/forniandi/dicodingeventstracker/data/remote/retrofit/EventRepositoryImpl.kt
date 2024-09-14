package rachman.forniandi.dicodingeventstracker.data.remote.retrofit

import androidx.lifecycle.liveData
import rachman.forniandi.dicodingeventstracker.data.remote.response.RemoteDataSource
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.DataMapper
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.RemoteResponse
import rachman.forniandi.dicodingeventstracker.domain.repository.EventsRepository
import javax.inject.Inject


class EventRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): EventsRepository {

    override fun getFutureEvents(active: Int)= liveData {
        emit(RemoteResponse.Loading())
        try {
            val response = remoteDataSource.getFutureEvents(active)
            val result = response.body()?.listEvents?.map { eventsResponse->
                DataMapper.mapEventsResponseToDomain(eventsResponse)
            }
            emit(RemoteResponse.Success(result))
        }catch (e: Exception) {
            emit(e.message?.let { RemoteResponse.Error(it) })
        }
    }


    override fun getPastEvents(active: Int)= liveData {
        emit(RemoteResponse.Loading())
        try {
            val response = remoteDataSource.getPastEvents(active)
            val result = response.body()?.listEvents?.map { eventsResponse->
                DataMapper.mapEventsResponseToDomain(eventsResponse)
            }
            emit(RemoteResponse.Success(result))
        }catch (e: Exception) {
            emit(e.message?.let { RemoteResponse.Error(it) })
        }
    }

    override fun getDetailEvents(id: Int)= liveData {
        emit(RemoteResponse.Loading())
        try {
            val response = remoteDataSource.getDetailEvents(id)
            val result = response.body()?.event?.let {
                DataMapper.mapDetailEventsResponseToDomain(it)
            }
            emit(RemoteResponse.Success(result))
        }catch (e: Exception) {
            emit(e.message?.let { RemoteResponse.Error(it) })
        }
    }

    override fun searchEvents(active: Int, query: String) = liveData {
        emit(RemoteResponse.Loading())
        try {
            val response = remoteDataSource.getSearchQueryEvent(active, query)
            val result = response.body()?.listEvents?.map { searchResponse->
                DataMapper.mapEventsResponseToDomain(searchResponse)
            }
            emit(RemoteResponse.Success(result))
        }catch (e: Exception) {
            emit(e.message?.let { RemoteResponse.Error(it) })
        }
    }
}