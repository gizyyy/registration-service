package com.kotlinplayground.domain.integrationevents.student

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import java.time.Instant

class StudentUnregisteredFromTeacherEvent(
    var schoolId: String,
    var studentId: Int,
    var teacherId: Int, override var occurredAt: Instant
) :
    IntegrationEvent {

}