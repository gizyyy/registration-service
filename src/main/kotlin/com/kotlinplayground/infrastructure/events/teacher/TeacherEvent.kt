package com.kotlinplayground.infrastructure.events.teacher

import com.kotlinplayground.infrastructure.events.school.SchoolEvent

interface TeacherEvent : SchoolEvent {
    var teacherId: Int
}