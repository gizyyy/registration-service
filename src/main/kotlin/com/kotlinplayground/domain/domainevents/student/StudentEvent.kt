package com.kotlinplayground.domain.domainevents.student

import com.kotlinplayground.domain.domainevents.ApplicationDomainEvent

interface StudentEvent : ApplicationDomainEvent {
    var studentId: Int
    var studentName:String
}