package com.kotlinplayground.domain.domainevents.student

import com.kotlinplayground.domain.integrationevents.student.StudentRemovedEvent
import java.time.Instant

class StudentRemovedEvent(
    var schoolId: String, override var studentId: Int,
    override var occurredAt: Instant, override var studentName: String
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