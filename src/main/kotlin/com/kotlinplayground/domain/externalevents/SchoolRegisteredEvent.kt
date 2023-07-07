package com.kotlinplayground.domain.externalevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.Student
import com.kotlinplayground.domain.Teacher

@JsonIgnoreProperties(ignoreUnknown = true)
data class SchoolRegisteredEvent(
    var school: School
) {
    companion object {
        const val type = "education.service.events.external.schools.SchoolRegisteredEvent"
    }
}