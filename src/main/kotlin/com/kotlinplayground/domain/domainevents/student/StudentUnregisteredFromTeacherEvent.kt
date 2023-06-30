package com.kotlinplayground.domain.domainevents.student

import com.kotlinplayground.domain.integrationevents.student.StudentUnregisteredFromTeacherEvent
import java.time.Instant

class StudentUnregisteredFromTeacherEvent(
    override var schoolId: String,
    override var studentId: Int,
    override var teacherId: Int, override var occurredAt: Instant
) :
    StudentRegisterEvent {
    override fun convertToIntegrationEvent(): StudentUnregisteredFromTeacherEvent {
        return StudentUnregisteredFromTeacherEvent(
            this.schoolId,
            this.studentId,
            this.teacherId,
            Instant.now()
        )
    }
}