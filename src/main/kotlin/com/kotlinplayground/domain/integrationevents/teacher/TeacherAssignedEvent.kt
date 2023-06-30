package com.kotlinplayground.domain.integrationevents.teacher

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import java.time.Instant

class TeacherAssignedEvent(
    var schoolId: String, var teacherId: Int,
    override var occurredAt: Instant
) :
    IntegrationEvent {

}