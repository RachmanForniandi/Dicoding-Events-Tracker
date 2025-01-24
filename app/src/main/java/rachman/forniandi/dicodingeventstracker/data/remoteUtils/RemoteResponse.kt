package rachman.forniandi.dicodingeventstracker.data.remoteUtils

sealed class RemoteResponse <out R>{
    data class Loading<out T>(val data: T? = null) : RemoteResponse<T>()

    data class Success<out T>(val data: T): RemoteResponse<T>()

    data class Error(val errorMessage: String): RemoteResponse<Nothing>()
}
