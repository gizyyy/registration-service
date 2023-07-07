package com.kotlinplayground.infrastructure.listener

import org.apache.commons.lang3.StringUtils

enum class RouteBinding(var type: String, var binding: String) {
    SCHOOL_ADDED("education.service.events.external.schools.SchoolRegisteredEvent", "school"),
    SCHOOL_DELETED("education.service.events.external.schools.SchoolDeletedEvent", "schoolEventReceived"),
    STUDENT_DELETED(
        "education.service.events.external.students.StudentDeletedEvent",
        "studentEventReceived"
    ),
    STUDENT_ADDED(
        "education.service.events.external.students.StudentRegisteredEvent",
        "studentEventReceived"
    );

    companion object {
        fun findBinding(key: String): String {
            return RouteBinding.values()
                .find { r: RouteBinding -> StringUtils.equals(r.type, key) }?.binding ?: "unknown"
        }
    }
}