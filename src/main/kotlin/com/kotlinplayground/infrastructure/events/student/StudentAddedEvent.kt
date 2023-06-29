package com.kotlinplayground.infrastructure.events.student

class StudentAddedEvent(override var schoolId: String, override var studentId: Int) :
    StudentEvent