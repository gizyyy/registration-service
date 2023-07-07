package com.kotlinplayground.domain.domainevents.student

import com.kotlinplayground.domain.integrationevents.student.StudentRemovedEvent
import java.time.Instant

class StudentRemovedEvent(
    override var schoolId: String, override var studentId: Int,
    override var occurredAt: Instant
) :
    StudentEvent {
    override fun convertToIntegrationEvent(): StudentRemovedEvent {
        return StudentRemovedEvent(
            this.studentId.toString(),
            this.schoolId,
            Instant.now()
        )
    }
}