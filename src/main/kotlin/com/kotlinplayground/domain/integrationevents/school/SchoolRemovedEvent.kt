package com.kotlinplayground.domain.integrationevents.school

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import java.time.Instant

class SchoolRemovedEvent(override var id: String, override var occurredAt: Instant) :
    IntegrationEvent {
    companion object {
        const val type = "education.service.events.internal.schools.SchoolRemovedEvent"
    }
}