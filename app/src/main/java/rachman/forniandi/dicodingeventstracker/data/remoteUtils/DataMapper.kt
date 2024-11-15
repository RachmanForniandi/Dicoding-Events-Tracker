package rachman.forniandi.dicodingeventstracker.data.remoteUtils

import rachman.forniandi.dicodingeventstracker.data.remote.response.Event
import rachman.forniandi.dicodingeventstracker.data.remote.response.EventsItem
import rachman.forniandi.dicodingeventstracker.domain.entity.Events

object DataMapper {

    fun mapEventsResponseToDomain(event: EventsItem):Events{
        return Events(
            id = event.id,
            name = event.name,
            summary = event.summary,
            description = event.description,
            imageLogo = event.imageLogo,
            mediaCover = event.mediaCover,
            category = event.category,
            ownerName = event.ownerName,
            cityName = event.cityName,
            quota = event.quota,
            registrants = event.registrants,
            beginTime = event.beginTime,
            endTime = event.endTime,
            link = event.link
        )
    }


    fun mapDetailEventsResponseToDomain(event: Event):Events{
        return Events(
            id = event.id,
            name = event.name,
            summary = event.summary,
            description = event.description,
            imageLogo = event.imageLogo,
            mediaCover = event.mediaCover,
            category = event.category,
            ownerName = event.ownerName,
            cityName = event.cityName,
            quota = event.quota,
            registrants = event.registrants,
            beginTime = event.beginTime,
            endTime = event.endTime,
            link = event.link
        )
    }
}