package com.kotlinplayground.domain.integrationevents.student

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import com.kotlinplayground.domain.integrationevents.school.SchoolAddedEvent
import java.time.Instant

class StudentChangedEvent(
    override var id: String,
    var schoolId: String,
    override var occurredAt: Instant
) :
    IntegrationEvent {
    companion object {
        const val type = "education.service.events.internal.schools.StudentChangedEvent"
    }
}