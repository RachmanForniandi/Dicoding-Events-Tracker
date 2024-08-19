package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rachman.forniandi.dicodingeventstracker.domain.EventsRepository
import javax.inject.Inject

class SearchEventsViewModel @Inject constructor(
    private val repository: EventsRepository
): ViewModel(){

    private val _activeValue = MutableLiveData<Int>()
    private val _queryValue = MutableLiveData<String>()

    fun setSearchParameters(activeValue:Int,query:String){
        _activeValue.value = activeValue
        _queryValue.value =  query
    }

    val searchEvents by lazy {

    }
}