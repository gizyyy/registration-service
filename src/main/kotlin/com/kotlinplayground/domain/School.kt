package com.kotlinplayground.domain

import com.kotlinplayground.application.exceptions.StudentNotFoundException
import com.kotlinplayground.application.exceptions.TeacherNotFoundException
import com.kotlinplayground.domain.domainevents.school.SchoolAddedEvent
import com.kotlinplayground.domain.domainevents.school.SchoolRemovedEvent
import com.kotlinplayground.domain.domainevents.student.*
import com.kotlinplayground.domain.domainevents.teacher.TeacherAssignedEvent
import com.kotlinplayground.domain.domainevents.teacher.TeacherUnassignedEvent
import com.kotlinplayground.infrastructure.SchoolIdGenerator
import com.kotlinplayground.infrastructure.TeacherIdGenerator
import com.kotlinplayground.infrastructure.repositories.StudentIdGenerator
import org.springframework.data.annotation.Id
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.util.CollectionUtils
import java.time.Instant

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
        registerEvent(SchoolAddedEvent(this.schoolId, Instant.now()))
        return true
    }

    fun unregister(): Boolean {
        registerEvent(SchoolRemovedEvent(this.schoolId, Instant.now()))
        return true
    }

    @Synchronized
    fun addTeacher(teacher: Teacher) {
        teacher.id = TeacherIdGenerator.generate()
        teachers.add(teacher)
        registerEvent(TeacherAssignedEvent(schoolId, teacher.id, Instant.now()))
    }

    @Synchronized
    fun changeTeacher(teacherId:Int ,teacher: Teacher) {
        val teacherFound = teachers.find { it.id == teacherId }
        val teacherExisting = teacherFound ?: throw TeacherNotFoundException(teacherId.toString())
        teacherExisting.address = teacher.address
        teacherExisting.name = teacher.name

        registerEvent(TeacherAssignedEvent(schoolId, teacher.id, Instant.now()))
    }

    private fun getStudent(studentId: Int): Student? {
        return try {
            students.first { x: Student -> x.id == studentId }
        } catch (e: NoSuchElementException) {
            null
        }
    }

    @Synchronized
    fun addStudent(student: Student) {
        student.id = StudentIdGenerator.generate()
        students.add(student)
        registerEvent(StudentAddedEvent(schoolId, student.id, Instant.now()))
    }

    @Synchronized
    fun replaceStudent(student: Student) {
        val foundStudent = this.students.find { s: Student -> s.id == student.id }
        val studentExisting = foundStudent ?: throw StudentNotFoundException(student.id.toString())
        studentExisting.name = student.name;
        studentExisting.address = student.address
        registerEvent(StudentChangedEvent(schoolId, student.id, Instant.now()))
    }

    @Synchronized
    fun removeTeacher(teacherId: Int) {
        if (CollectionUtils.isEmpty(teachers)) {
            return
        }

        val removed = teachers.removeIf { x: Teacher -> x.id == teacherId }
        if (!removed)
            return

        students.forEach {
            it.registeredTeachers.removeIf { tId: Int -> tId == teacherId }
        }
        registerEvent(TeacherUnassignedEvent(schoolId, teacherId, Instant.now()))
    }

    @Synchronized
    fun removeStudent(studentId: Int) {
        if (CollectionUtils.isEmpty(students)) {
            return
        }

        val removed = students.removeIf { x: Student -> x.id == studentId }
        if (!removed)
            return
        registerEvent(StudentRemovedEvent(schoolId, studentId, Instant.now()))
    }

    fun getTeacher(teacherId: Int): Teacher? {
        return try {
            teachers.first { x: Teacher -> x.id == teacherId }
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun registerStudentToTeacher(studentId: Int, teacherId: Int) {
        val student = getStudent(studentId) ?: throw StudentNotFoundException(studentId.toString())
        student.registeredTeachers.find { x: Int -> x == teacherId }
            ?: student.registeredTeachers.add(teacherId)
        registerEvent(
            StudentRegisteredToTeacherEvent(
                this.schoolId,
                studentId,
                teacherId,
                Instant.now()
            )
        )
    }

    fun unregisterStudentFromTeacher(studentId: Int, teacherId: Int) {
        val student = getStudent(studentId) ?: throw StudentNotFoundException(studentId.toString())
        val removed = student.registeredTeachers.removeIf { x: Int -> x == teacherId }
        if (!removed)
            return

        registerEvent(
            StudentUnregisteredFromTeacherEvent(
                this.schoolId,
                studentId,
                teacherId,
                Instant.now()
            )
        )
    }
}
