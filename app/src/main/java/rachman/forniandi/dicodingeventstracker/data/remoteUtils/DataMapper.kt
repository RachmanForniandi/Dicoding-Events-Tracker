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

    /*fun mapEventEntityToDomain(eventEntity: List<EventEntity>): Events {
        val eventsList = eventEntity.map { (events) ->  // Destructure to access 'events' directly
            Events(
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
        return Events(eventEntity = eventsList)
    }*/

    /*fun mapEventDomainToEntity(events: Events): EventEntity {
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
    }*/

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