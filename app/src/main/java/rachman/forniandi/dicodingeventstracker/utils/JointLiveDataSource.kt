package rachman.forniandi.dicodingeventstracker.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class JointLiveDataSource <A, B,C>(a: LiveData<A>, b: LiveData<B>,c:LiveData<C>) : MediatorLiveData<Pair<A?, B?>>(){
    init {
        addSource(a) { value = it to b.value }
        addSource(b) { value = a.value to it }

    }
}