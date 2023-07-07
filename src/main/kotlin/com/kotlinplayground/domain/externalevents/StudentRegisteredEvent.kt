package com.kotlinplayground.domain.externalevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.kotlinplayground.domain.Student
import java.time.Instant

@JsonIgnoreProperties(ignoreUnknown = true)
data class StudentRegisteredEvent(
    var schoolId: String, var student: Student,
    var occurredAt: Instant
) {
    companion object {
        const val type = "education.service.events.external.students.StudentRegisteredEvent"
    }
}