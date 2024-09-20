package rachman.forniandi.dicodingeventstracker.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import java.io.Serializable

@Entity(tableName = "events_table")
class EventEntity(
    var events: Events
){
    @PrimaryKey(autoGenerate = true)
    var idNo: Int=0
}
