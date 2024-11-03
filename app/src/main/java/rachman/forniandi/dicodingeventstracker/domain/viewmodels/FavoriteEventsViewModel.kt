package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rachman.forniandi.dicodingeventstracker.data.local.room.EventEntity
import rachman.forniandi.dicodingeventstracker.domain.repository.FavoriteEventRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteEventsViewModel @Inject constructor(
    private val favoriteEventRepository: FavoriteEventRepository
) : ViewModel() {

    val showFavoriteEvents : LiveData<List<EventEntity>> = favoriteEventRepository.showFavEvent().asLiveData()

    fun clearAllFavoriteEvents() = viewModelScope.launch(Dispatchers.IO) {
        favoriteEventRepository.deleteAllFavEvent()
    }


}