package com.kotlinplayground.domain.domainevents.teacher

import com.kotlinplayground.domain.integrationevents.teacher.TeacherUnassignedEvent
import java.time.Instant

class TeacherUnassignedEvent(
    override var schoolId: String, override var teacherId: Int,
    override var occurredAt: Instant
) :
    TeacherEvent {
    override fun convertToIntegrationEvent(): TeacherUnassignedEvent {
        return TeacherUnassignedEvent(
            this.schoolId,
            this.teacherId,
            this.occurredAt
        )
    }
}