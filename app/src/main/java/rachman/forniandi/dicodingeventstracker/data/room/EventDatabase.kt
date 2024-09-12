package rachman.forniandi.dicodingeventstracker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rachman.forniandi.dicodingeventstracker.data.local.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 1
)
abstract class EventDatabase:RoomDatabase() {
    abstract fun getEventDao():EventDao

    companion object{
        @Volatile
        private var instanceDb: EventDatabase?= null

        fun getInstance(context: Context) =
            instanceDb ?: synchronized(this) {
                instanceDb ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event_db"
                ).build()
            }.also {
                instanceDb = it
            }
    }
}