package rachman.forniandi.dicodingeventstracker.domain.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import rachman.forniandi.dicodingeventstracker.domain.EventsRepository
import rachman.forniandi.dicodingeventstracker.utils.JointLiveDataSource
import javax.inject.Inject

@HiltViewModel
class SearchEventsViewModel @Inject constructor(
    private val repository: EventsRepository
): ViewModel(){

    private val _query = MutableLiveData<String>()
    private val _activeValue= MutableLiveData<Int>()
    private val fetchSearchEvents = MutableLiveData<Unit>()

    fun fetchSearchEvent(active:Int,query: String) {
        fetchSearchEvents.value = Unit
        _query.value = query
        _activeValue.value = active
    }


    val searchEvents by lazy {
        JointLiveDataSource(_activeValue,_query,fetchSearchEvents).switchMap { //value, query, _ ->
            repository.searchEvents(it.first ?: 0, it.second.toString())
        }
    }

    /*val searchEvents by lazy {
            JointLiveDataSource(_queryValue, fetchSearchUsers).switchMap {
                repository.searchEvents(it.first.toString())
                    .catch { e ->
                        // Handle the exception here
                        Log.e("SearchEventsViewModel", "Error during search", e) // Log the error
                        // Optionally, emit an error state to your UI
                        // _searchErrorState.value = e.message ?: "Unknown error"
                    }.flowOn(Dispatchers.IO) // Perform search on a background thread
            }
        }*/

}