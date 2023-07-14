package com.kotlinplayground.domain.domainevents.student

import com.kotlinplayground.domain.integrationevents.student.StudentAddedEvent
import java.time.Instant

class StudentAddedEvent(
    var schoolId: String, override var studentName: String, override var studentId: Int,
    override var occurredAt: Instant
) :
    StudentEvent {
    override fun convertToIntegrationEvent(): StudentAddedEvent {
        return StudentAddedEvent(
            this.studentId.toString(),
            this.studentName,
            this.schoolId,
            this.occurredAt
        )
    }
}