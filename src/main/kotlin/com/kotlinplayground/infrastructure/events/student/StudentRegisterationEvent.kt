package com.kotlinplayground.infrastructure.events.student

import com.kotlinplayground.infrastructure.events.teacher.TeacherEvent

interface StudentRegisterEvent : TeacherEvent {
    var studentId: Int
}