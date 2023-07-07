package com.kotlinplayground.domain.integrationevents

import java.time.Instant

interface IntegrationEvent {
    var id: String
    var occurredAt: Instant

    companion object {
        const val type: String = ""
    }
}