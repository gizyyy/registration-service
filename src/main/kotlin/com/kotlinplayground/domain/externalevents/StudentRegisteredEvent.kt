package com.kotlinplayground.domain.externalevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.Instant

@JsonIgnoreProperties(ignoreUnknown = true)
data class StudentRegisteredEvent(
    var schoolId: String, var studentId: Int,
    var occurredAt: Instant
)