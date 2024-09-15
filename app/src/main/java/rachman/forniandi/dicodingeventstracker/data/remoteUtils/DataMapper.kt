package rachman.forniandi.dicodingeventstracker.data.remoteUtils

import rachman.forniandi.dicodingeventstracker.data.local.room.EventEntity
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

    fun mapEventEntityToDomain(eventEntity: EventEntity):Events{
        return Events(
            id = eventEntity.id,
            name = eventEntity.name,
            summary = eventEntity.summary,
            description = eventEntity.description,
            imageLogo = eventEntity.imageLogo,
            mediaCover = eventEntity.mediaCover,
            category = eventEntity.category,
            ownerName = eventEntity.ownerName,
            cityName = eventEntity.cityName,
            quota = eventEntity.quota,
            registrants = eventEntity.registrants,
            beginTime = eventEntity.beginTime,
            endTime = eventEntity.endTime,
            link = eventEntity.link
        )
    }

    fun mapEventDomainToEntity(events: Events): EventEntity {
        return EventEntity(
            id = events.id,
            name = events.name,
            summary = events.summary,
            description = events.description,
            imageLogo = events.imageLogo,
            mediaCover = events.mediaCover,
            category = events.category,
            ownerName = events.ownerName,
            cityName = events.cityName,
            quota = events.quota,
            registrants = events.registrants,
            beginTime = events.beginTime,
            endTime = events.endTime,
            link = events.link
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