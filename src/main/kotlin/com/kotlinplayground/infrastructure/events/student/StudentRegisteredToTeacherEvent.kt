package com.kotlinplayground.infrastructure.events.student

class StudentRegisteredToTeacherEvent(
    override var schoolId: String,
    override var studentId: Int,
    override var teacherId: Int
) :
    StudentRegisterEvent