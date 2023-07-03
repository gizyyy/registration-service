package com.kotlinplayground.domain.integrationevents.student

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import com.kotlinplayground.domain.integrationevents.school.SchoolAddedEvent
import java.time.Instant

class StudentChangedEvent(
    var schoolId: String, var studentId: Int,
    override var occurredAt: Instant
) :
    IntegrationEvent {

}