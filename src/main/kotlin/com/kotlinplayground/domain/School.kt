package com.kotlinplayground.domain

import com.kotlinplayground.domain.domainevents.school.SchoolAddedEvent
import com.kotlinplayground.domain.domainevents.student.StudentAddedEvent
import com.kotlinplayground.domain.domainevents.student.StudentRegisteredToTeacherEvent
import com.kotlinplayground.domain.domainevents.student.StudentRemovedEvent
import com.kotlinplayground.domain.domainevents.student.StudentUnregisteredFromTeacherEvent
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
        registerEvent(SchoolAddedEvent(this.schoolId, Instant.now()))
        return true
    }

    @Synchronized
    fun addTeacher(teacher: Teacher) {
        //mongoda autoincrement
        teacher.id = TeacherIdGenerator.generate()
        //zaten varsa teacher exist bunu da bad requeste maple
        teachers.add(teacher)
        registerEvent(TeacherAssignedEvent(schoolId, teacher.id, Instant.now()))
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
        registerEvent(StudentAddedEvent(schoolId, student.id, Instant.now()))
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
        registerEvent(TeacherUnassignedEvent(schoolId, teacherId, Instant.now()))
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
        val student = getStudent(studentId)

        if (student == null) {
            // yoksa bad req
            return
        }
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
        val student = getStudent(studentId)

        if (student == null) {
            // yoksa bad req
            return
        }
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
