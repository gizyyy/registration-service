package com.kotlinplayground.domain.domainevents.teacher

import com.kotlinplayground.domain.domainevents.school.SchoolEvent

interface TeacherEvent : SchoolEvent {
    var teacherId: Int
}