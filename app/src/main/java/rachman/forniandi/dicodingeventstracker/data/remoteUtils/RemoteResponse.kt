package rachman.forniandi.dicodingeventstracker.data.remoteUtils

/*sealed class RemoteResponse <R>(val data: R? = null, val message: String? = null){
    class Loading<T> :RemoteResponse<T>()
    class Success<T>(data: T?): RemoteResponse<T>(data)
    class Error<T>(message: String): RemoteResponse<T>( message = message)
}*/
sealed class RemoteResponse <out R>{
    data class Loading<out T>(val data: T? = null) : RemoteResponse<T>()

    data class Success<out T>(val data: T): RemoteResponse<T>()

    data class Error(val errorMessage: String): RemoteResponse<Nothing>()
}
