package com.kotlinplayground.domain.domainevents.school

import com.kotlinplayground.domain.integrationevents.school.SchoolAddedEvent
import java.time.Instant

class SchoolAddedEvent(override var schoolId: String, var schoolName: String,override var occurredAt: Instant) :
    SchoolEvent {
    override fun convertToIntegrationEvent(): SchoolAddedEvent {
        return SchoolAddedEvent(this.schoolId, this.schoolName, this.occurredAt)
    }
}