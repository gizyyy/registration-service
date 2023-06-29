package com.kotlinplayground.infrastructure.events.teacher

class TeacherAssignedEvent(override var schoolId: String, override var teacherId: Int) :
    TeacherEvent