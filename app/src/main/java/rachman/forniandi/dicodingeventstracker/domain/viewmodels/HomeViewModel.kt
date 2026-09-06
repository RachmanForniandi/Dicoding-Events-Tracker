package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import rachman.forniandi.dicodingeventstracker.domain.repository.EventsRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: EventsRepository
): ViewModel() {

    private val _activeValueUpcoming = MutableLiveData<Int>()
    private val _activeValuePast = MutableLiveData<Int>()

    fun setValueActiveUpcoming(active:Int){
        _activeValueUpcoming.value = active
    }
    fun setValueActivePast(active:Int){
        _activeValuePast.value = active
    }

    val upcomingEvent by lazy {
        _activeValueUpcoming.switchMap { active ->
            repository.getFutureEvents(active)
        }
    }

    val pastEvent by lazy {
        _activeValuePast.switchMap { active ->
            repository.getPastEvents(active)
        }
    }


}