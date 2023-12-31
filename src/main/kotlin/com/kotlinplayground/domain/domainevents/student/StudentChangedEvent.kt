package com.kotlinplayground.domain.domainevents.student

import com.kotlinplayground.domain.integrationevents.student.StudentAddedEvent
import com.kotlinplayground.domain.integrationevents.student.StudentChangedEvent
import java.time.Instant

class StudentChangedEvent(
    var schoolId: String, override var studentId: Int,
    override var occurredAt: Instant, override var studentName: String
) :
    StudentEvent {
    override fun convertToIntegrationEvent(): StudentChangedEvent {
        return StudentChangedEvent(this.studentId.toString(), this.schoolId, this.occurredAt)
    }
}