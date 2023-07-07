package com.kotlinplayground.domain.domainevents.student

import com.kotlinplayground.domain.integrationevents.student.StudentAddedEvent
import com.kotlinplayground.domain.integrationevents.student.StudentRegisteredToTeacherEvent
import java.time.Instant

class StudentRegisteredToTeacherEvent(
    override var schoolId: String,
    override var studentId: Int,
    override var teacherId: Int, override var occurredAt: Instant
) :
    StudentRegisterEvent {
    override fun convertToIntegrationEvent(): StudentRegisteredToTeacherEvent {
        return StudentRegisteredToTeacherEvent(
            this.studentId.toString(),
            this.schoolId,
            this.teacherId,
            Instant.now()
        )
    }
}