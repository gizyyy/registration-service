package com.kotlinplayground.domain.externalevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.kotlinplayground.domain.Student
import com.kotlinplayground.domain.Teacher

@JsonIgnoreProperties(ignoreUnknown = true)
data class SchoolRegisteredEvent(
    var schoolId: String,
    var name: String,
    var teachers: ArrayList<Teacher>,
    var students: ArrayList<Student>
) {
    companion object {
        const val type = "education.service.events.external.schools.SchoolRegisteredEvent"
    }
}