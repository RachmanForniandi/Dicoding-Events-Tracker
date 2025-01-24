package rachman.forniandi.dicodingeventstracker.domain.viewmodels


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import rachman.forniandi.dicodingeventstracker.domain.repository.EventsRepository
import javax.inject.Inject

@HiltViewModel
class PastEventsViewmodel @Inject constructor(
    private val repository: EventsRepository,
    application: Application
): AndroidViewModel(application) {

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