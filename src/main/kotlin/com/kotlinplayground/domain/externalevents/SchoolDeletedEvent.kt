package com.kotlinplayground.domain.externalevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SchoolDeletedEvent(
    var schoolId: String
) {
    companion object {
        const val type = "education.service.events.external.schools.SchoolDeletedEvent"
    }
}