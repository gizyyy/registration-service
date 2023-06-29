package com.kotlinplayground.domain

import com.kotlinplayground.infrastructure.SchoolIdGenerator
import com.kotlinplayground.infrastructure.TeacherIdGenerator
import com.kotlinplayground.infrastructure.events.*
import com.kotlinplayground.infrastructure.events.school.SchoolAddedEvent
import com.kotlinplayground.infrastructure.events.student.StudentAddedEvent
import com.kotlinplayground.infrastructure.events.student.StudentRegisteredToTeacherEvent
import com.kotlinplayground.infrastructure.events.student.StudentRemovedEvent
import com.kotlinplayground.infrastructure.events.student.StudentUnregisteredFromTeacherEvent
import com.kotlinplayground.infrastructure.events.teacher.TeacherAssignedEvent
import com.kotlinplayground.infrastructure.events.teacher.TeacherUnassignedEvent
import com.kotlinplayground.infrastructure.repositories.StudentIdGenerator
import org.springframework.data.annotation.Id
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.util.CollectionUtils

@Document("schools")
data class School(
    @Id
    var schoolId: String,
    var name: String,
    var teachers: ArrayList<Teacher> = arrayListOf(),
    var students: ArrayList<Student> = arrayListOf()

) : AbstractAggregateRoot<School>() {

    fun register(): Boolean {
        this.schoolId = SchoolIdGenerator.generate().toString()
        registerEvent(SchoolAddedEvent(this.schoolId))
        return true
    }

    fun unregister(): Boolean {
        registerEvent(SchoolAddedEvent(this.schoolId))
        return true
    }

    @Synchronized
    fun addTeacher(teacher: Teacher) {
        //mongoda autoincrement
        teacher.id = TeacherIdGenerator.generate()
        //zaten varsa teacher exist bunu da bad requeste maple
        teachers.add(teacher)
        registerEvent(TeacherAssignedEvent(schoolId, teacher.id))
    }

    fun getStudent(studentId: Int): Student? {
        return try {
            students.first { x: Student -> x.id == studentId }
        } catch (e: NoSuchElementException) {
            null
        }
    }


    @Synchronized
    fun addStudent(student: Student) {
        //mongoda autoincrement
        student.id = StudentIdGenerator.generate()
        //zaten varsa exist bunu da bad requeste maple
        students.add(student)
        registerEvent(StudentAddedEvent(schoolId, student.id))
    }

    @Synchronized
    fun removeTeacher(teacherId: Int) {
        if (CollectionUtils.isEmpty(teachers)) {
            return
        }

        val removed = teachers.removeIf { x: Teacher -> x.id == teacherId }
        //zaten yoksa teacher bunu da bad requeste maple
        if (!removed)
            return
        registerEvent(TeacherUnassignedEvent(schoolId, teacherId))
    }

    @Synchronized
    fun removeStudent(studentId: Int) {
        if (CollectionUtils.isEmpty(students)) {
            return
        }

        val removed = students.removeIf { x: Student -> x.id == studentId }
        //zaten yoksa bunu da bad requeste maple
        if (!removed)
            return
        registerEvent(StudentRemovedEvent(schoolId, studentId))
    }

    fun getTeacher(teacherId: Int): Teacher? {
        return try {
            teachers.first { x: Teacher -> x.id == teacherId }
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun registerStudentToTeacher(studentId: Int, teacherId: Int) {
        val student = getStudent(studentId)

        if (student == null) {
            // yoksa bad req
            return
        }
        student.registeredTeachers.find { x: Int -> x == teacherId }
            ?: student.registeredTeachers.add(teacherId)
        registerEvent(StudentRegisteredToTeacherEvent(this.schoolId, studentId, teacherId))
    }

    fun unregisterStudentFromTeacher(studentId: Int, teacherId: Int) {
        val student = getStudent(studentId)

        if (student == null) {
            // yoksa bad req
            return
        }
        val removed = student.registeredTeachers.removeIf { x: Int -> x == teacherId }
        if (!removed)
            return

        registerEvent(StudentUnregisteredFromTeacherEvent(this.schoolId, studentId, teacherId))
    }
}
