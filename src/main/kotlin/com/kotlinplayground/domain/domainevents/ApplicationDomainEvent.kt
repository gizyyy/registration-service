package com.kotlinplayground.domain.domainevents

import com.kotlinplayground.domain.integrationevents.IntegrationEvent
import java.time.Instant

interface ApplicationDomainEvent {
    var occurredAt: Instant

    fun convertToIntegrationEvent(): IntegrationEvent
}