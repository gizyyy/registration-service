package com.kotlinplayground.infrastructure.events.teacher

class TeacherUnassignedEvent(override var schoolId: String, override var teacherId: Int) :
    TeacherEvent