package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import rachman.forniandi.dicodingeventstracker.domain.repository.EventsRepository
import javax.inject.Inject

@HiltViewModel
class DetailEventViewModel @Inject constructor(
    private val repository: EventsRepository
): ViewModel() {


    private val _idEventValue = MutableLiveData<Int>()

    fun setValueId(idEvent:Int){
        _idEventValue.value =idEvent
    }

    val eventDetail by lazy {
        _idEventValue.switchMap { idEvent ->
            repository.getDetailEvents(idEvent)
        }
    }
}