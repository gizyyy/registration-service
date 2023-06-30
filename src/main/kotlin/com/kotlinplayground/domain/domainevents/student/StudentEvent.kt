package com.kotlinplayground.domain.domainevents.student

import com.kotlinplayground.domain.domainevents.school.SchoolEvent

interface StudentEvent : SchoolEvent {
    var studentId: Int
}