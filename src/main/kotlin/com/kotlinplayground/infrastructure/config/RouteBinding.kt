package com.kotlinplayground.infrastructure.config

import org.apache.commons.lang3.StringUtils

enum class RouteBinding(var type: String, var binding: String) {
    SCHOOL_ADDED(
        "education.service.events.external.schools.SchoolRegisteredEvent",
        "consumeSchoolRegisteredEvent"
    ),
    SCHOOL_DELETED(
        "education.service.events.external.schools.SchoolDeletedEvent",
        "consumeSchoolDeletedEvent"
    ),
    STUDENT_DELETED(
        "education.service.events.external.students.StudentDeletedEvent",
        "consumeStudentDeletedEvent"
    ),
    STUDENT_ADDED(
        "education.service.events.external.students.StudentRegisteredEvent",
        "consumeStudentRegisteredEvent"
    );

    companion object {
        fun findBinding(key: String): String {
            return RouteBinding.values()
                .find { r: RouteBinding -> StringUtils.equals(r.type, key) }?.binding ?: "unknown"
        }
    }
}