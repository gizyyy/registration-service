package com.kotlinplayground.domain.integrationevents.school

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import java.time.Instant

class SchoolAddedEvent(
    override var id: String,
    var schoolName: String,
    override var occurredAt: Instant
) :
    IntegrationEvent {
    companion object {
        const val type = "education.service.events.internal.schools.SchoolRegisteredEvent"
    }
}