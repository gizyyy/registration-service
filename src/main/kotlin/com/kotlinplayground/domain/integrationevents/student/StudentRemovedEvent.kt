package com.kotlinplayground.domain.integrationevents.student

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import java.time.Instant

class StudentRemovedEvent(
    override var id: String,
    var schoolId: String,
    override var occurredAt: Instant
) :
    IntegrationEvent {
    companion object {
        const val type = "education.service.events.internal.schools.StudentRemovedEvent"
    }
}