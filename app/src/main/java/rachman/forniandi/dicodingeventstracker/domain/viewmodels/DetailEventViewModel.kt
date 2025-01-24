package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rachman.forniandi.dicodingeventstracker.data.local.room.EventEntity
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import rachman.forniandi.dicodingeventstracker.domain.repository.EventsRepository
import rachman.forniandi.dicodingeventstracker.domain.repository.FavoriteEventRepository
import javax.inject.Inject

@HiltViewModel
class DetailEventViewModel @Inject constructor(
    private val repository: EventsRepository,
    private val favoriteEventRepository: FavoriteEventRepository
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

    val readToCheckEvents: LiveData<List<EventEntity>> = favoriteEventRepository.showFavEvent().asLiveData()

    fun actionInsertFavEvent(events: EventEntity) = viewModelScope.launch {
        favoriteEventRepository.insertFavEvent(events)
    }

    fun actionDeleteFavEvent(events: EventEntity) = viewModelScope.launch {
        favoriteEventRepository.deleteFavEvent(events)
    }




}