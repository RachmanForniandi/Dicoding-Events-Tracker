package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.RemoteResponse
import rachman.forniandi.dicodingeventstracker.domain.EventsRepository
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
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

    /*private fun hasInternetConnection():Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        )as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities= connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
            else -> false
        }
    }*/
}