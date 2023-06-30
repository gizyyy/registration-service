package com.kotlinplayground.domain.domainevents.student

import com.kotlinplayground.domain.domainevents.teacher.TeacherEvent

interface StudentRegisterEvent : TeacherEvent {
    var studentId: Int
}