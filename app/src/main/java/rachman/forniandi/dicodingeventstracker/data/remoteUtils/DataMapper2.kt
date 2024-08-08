package rachman.forniandi.dicodingeventstracker.data.remoteUtils

import rachman.forniandi.dicodingeventstracker.data.remote.response.Event
import rachman.forniandi.dicodingeventstracker.data.remote.response.EventsItem
import rachman.forniandi.dicodingeventstracker.data.remote.response.ResponseEvents
import rachman.forniandi.dicodingeventstracker.domain.entity.Events

fun List<EventsItem>.toDomainEvents() = map {
    EventsItem(it.summary,
        it.mediaCover,
        it.registrants,
        it.imageLogo,
        it.link,
        it.description,
        it.ownerName,
        it.cityName,
        it.quota,it.name,
        it.id,
        it.category,
        it.beginTime,
        it.endTime)
}