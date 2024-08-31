package rachman.forniandi.dicodingeventstracker.domain.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import rachman.forniandi.dicodingeventstracker.domain.EventsRepository
import javax.inject.Inject

@HiltViewModel
class PastEventsViewmodel @Inject constructor(
    private val repository: EventsRepository
): ViewModel() {

    private val _activeValue = MutableLiveData<Int>()

    fun setValueActive(active:Int){
        _activeValue.value =active
    }

    val pastEvent by lazy {
        _activeValue.switchMap { active ->
            repository.getPastEvents(active)
        }
    }

}