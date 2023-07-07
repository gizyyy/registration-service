package com.kotlinplayground.domain.integrationevents.student

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import java.time.Instant

class StudentRegisteredToTeacherEvent(
    override var id: String,
    var schoolId: String,
    var teacherId: Int, override var occurredAt: Instant
) :
    IntegrationEvent {
    companion object {
        const val type = "education.service.events.internal.schools.StudentRegisteredToTeacherEvent"
    }
}