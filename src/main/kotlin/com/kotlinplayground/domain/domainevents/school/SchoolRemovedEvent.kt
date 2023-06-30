package com.kotlinplayground.domain.domainevents.school

import com.kotlinplayground.domain.integrationevents.school.SchoolRemovedEvent
import java.time.Instant

class SchoolRemovedEvent(override var schoolId: String, override var occurredAt: Instant) :
    SchoolEvent {
    override fun convertToIntegrationEvent(): SchoolRemovedEvent {
        return SchoolRemovedEvent(
            this.schoolId,
            this.occurredAt
        )
    }
}