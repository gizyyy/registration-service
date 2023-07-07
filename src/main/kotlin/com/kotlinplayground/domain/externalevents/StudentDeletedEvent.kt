package com.kotlinplayground.domain.externalevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.Instant

@JsonIgnoreProperties(ignoreUnknown = true)
data class StudentDeletedEvent(
    var schoolId: String, var studentId: Int,
    var occurredAt: Instant
){
    companion object {
        const val type = "education.service.events.external.students.StudentDeletedEvent"
    }
}