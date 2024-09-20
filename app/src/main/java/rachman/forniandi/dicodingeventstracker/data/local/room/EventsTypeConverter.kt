package rachman.forniandi.dicodingeventstracker.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rachman.forniandi.dicodingeventstracker.domain.entity.Events

class EventsTypeConverter {

    var gson= Gson()

    @TypeConverter
    fun eventsToString(events: Events):String{
        return gson.toJson(events)
    }

    @TypeConverter
    fun stringToEvents(data:String):Events{
        val eventType= object : TypeToken<Events>(){}.type
        return gson.fromJson(data,eventType)
    }
}