package com.kotlinplayground.domain.domainevents.teacher

import com.kotlinplayground.domain.integrationevents.teacher.TeacherAssignedEvent
import java.time.Instant

class TeacherAssignedEvent(
    override var schoolId: String, override var teacherId: Int,
    override var occurredAt: Instant
) :
    TeacherEvent {
    override fun convertToIntegrationEvent(): TeacherAssignedEvent {
        return TeacherAssignedEvent(
            this.teacherId.toString(),
            this.schoolId,
            this.occurredAt
        )
    }
}