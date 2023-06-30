package com.kotlinplayground.domain.integrationevents.student

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import java.time.Instant

class StudentRemovedEvent(
    var schoolId: String, var studentId: Int,
    override var occurredAt: Instant
) :
    IntegrationEvent