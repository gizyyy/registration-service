package com.kotlinplayground.infrastructure.events.student

class StudentRemovedEvent(override var schoolId: String, override var studentId: Int) :
    StudentEvent