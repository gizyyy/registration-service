package com.kotlinplayground.infrastructure.events.student

import com.kotlinplayground.infrastructure.events.school.SchoolEvent

interface StudentEvent : SchoolEvent {
    var studentId: Int
}