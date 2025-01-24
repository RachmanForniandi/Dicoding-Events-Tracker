package rachman.forniandi.dicodingeventstracker.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class JointLiveDataSource <A, B,C>(a: LiveData<A>, b: LiveData<B>,c:LiveData<C>) : MediatorLiveData<Triple<A?, B?,C?>>(){
    init {
        addSource(a) { valueA ->
            value = Triple(valueA, b.value, c.value)
        }

        addSource(b) { valueB ->
            value = Triple(a.value, valueB, c.value)
        }

        addSource(c) { valueC ->
            value = Triple(a.value, b.value, valueC)
        }
    }
}