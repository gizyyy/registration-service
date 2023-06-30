package com.kotlinplayground.domain.domainevents.school

import com.kotlinplayground.domain.domainevents.ApplicationDomainEvent

interface SchoolEvent : ApplicationDomainEvent {
    var schoolId: String
}