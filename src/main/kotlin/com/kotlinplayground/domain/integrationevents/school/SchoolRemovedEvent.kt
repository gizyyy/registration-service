package com.kotlinplayground.domain.integrationevents.school

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import java.time.Instant

class SchoolRemovedEvent(var schoolId: String, override var occurredAt: Instant) :
    IntegrationEvent