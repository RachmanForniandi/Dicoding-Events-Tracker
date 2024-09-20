package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import rachman.forniandi.dicodingeventstracker.data.local.room.EventEntity
import rachman.forniandi.dicodingeventstracker.domain.repository.FavoriteEventRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteEventsViewModel @Inject constructor(
    favoriteEventRepository: FavoriteEventRepository
) : ViewModel() {

    val showFavoriteEvents : LiveData<List<EventEntity>> = favoriteEventRepository.showFavEvent().asLiveData()
}