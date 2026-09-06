package rachman.forniandi.dicodingeventstracker.data.repository


import rachman.forniandi.dicodingeventstracker.domain.repository.FavoriteEventRepository
import rachman.forniandi.dicodingeventstracker.data.local.LocalDataSource
import rachman.forniandi.dicodingeventstracker.data.local.room.EventEntity

class FavoriteEventRepositoryImpl(private val localDataSource: LocalDataSource
): FavoriteEventRepository {

    override suspend fun insertFavEvent(eventEntity: EventEntity) {
        localDataSource.insertFavoriteEvent(eventEntity)
    }

    override suspend fun deleteFavEvent(eventEntity: EventEntity) {
        localDataSource.deleteFavoriteEvent(eventEntity)
    }

    override fun showFavEvent()= localDataSource.showFavoriteEvent()

    override fun deleteAllFavEvent() = localDataSource.deleteAllFavoriteEvent()


    companion object {
        @Volatile
        private var instance: FavoriteEventRepositoryImpl? = null

        fun getInstanceForFavorite(localDataSource: LocalDataSource) =
            instance ?: synchronized(this) {
                instance ?: FavoriteEventRepositoryImpl(localDataSource)
            }.also {
                instance = it
            }
    }
}