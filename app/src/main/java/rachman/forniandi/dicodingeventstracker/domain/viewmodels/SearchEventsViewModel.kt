package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rachman.forniandi.dicodingeventstracker.domain.EventsRepository
import rachman.forniandi.dicodingeventstracker.utils.JointLiveDataSource
import javax.inject.Inject

class SearchEventsViewModel @Inject constructor(
    private val repository: EventsRepository
): ViewModel(){



    private val _activeValue = MutableStateFlow(0) // Initial value
    val activeValue: StateFlow<Int> = _activeValue.asStateFlow()

    private val _queryValue = MutableStateFlow("")
    val queryValue:StateFlow<String> = _queryValue.asStateFlow()

    private val fetchSearchUsers = MutableSharedFlow<Unit>()

    fun setSearchParameters(activeValue:Int,query:String){
        viewModelScope.launch {fetchSearchUsers.emit(Unit)
        }
        _activeValue.value = activeValue
        _queryValue.value = query
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