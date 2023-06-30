package com.kotlinplayground.domain.integrationevents

import java.time.Instant

interface IntegrationEvent {
    var occurredAt: Instant
}