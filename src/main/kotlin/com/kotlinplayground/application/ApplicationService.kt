package com.kotlinplayground.application

import com.kotlinplayground.application.exceptions.SchoolNotFoundException
import com.kotlinplayground.application.exceptions.StudentNotFoundException
import com.kotlinplayground.application.exceptions.TeacherNotFoundException
import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.Student
import com.kotlinplayground.domain.Teacher
import com.kotlinplayground.infrastructure.repositories.SchoolRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
class ApplicationService(private val schoolRepository: SchoolRepository) {

    @Transactional
    fun addSchool(school: School): Boolean {
        school.register()
        schoolRepository.save(school)
        return true
    }

    @Transactional
    @Throws(SchoolNotFoundException::class)
    fun addTeacherToSchool(schoolId: String, teacher: Teacher) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.addTeacher(teacher)
        schoolRepository.save(school)
    }

    @Transactional
    @Throws(SchoolNotFoundException::class)
    fun changeTheTeacher(schoolId: String, teacherId: Int ,teacher: Teacher) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.changeTeacher(teacherId, teacher)
        schoolRepository.save(school)
    }

    @Transactional
    @Throws(SchoolNotFoundException::class)
    fun addStudentToSchool(schoolId: String, student: Student) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.addStudent(student)
        schoolRepository.save(school)
    }

    @Transactional
    @Throws(SchoolNotFoundException::class)
    fun changeStudentData(student: Student, schoolId: String) {
        val school = getSchool(schoolId)
        val existingSchool = school ?: throw SchoolNotFoundException(schoolId)
        existingSchool.replaceStudent(student)
        schoolRepository.save(existingSchool)
    }

    @Transactional
    @Throws(SchoolNotFoundException::class, StudentNotFoundException::class)
    fun registerStudentToTeacher(studentId: Int, teacherId: Int) {
        val school = schoolRepository.findByStudentsId(studentId)
        val existingSchool = school ?: throw SchoolNotFoundException("")
        existingSchool.registerStudentToTeacher(studentId, teacherId)
        schoolRepository.save(school)
    }

    @Transactional
    @Throws(SchoolNotFoundException::class)
    fun removeStudentFromSchool(schoolId: String, studentId: Int) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.removeStudent(studentId)
        schoolRepository.save(school)
    }

    @Transactional
    @Throws(SchoolNotFoundException::class)
    fun removeTeacherFromSchool(schoolId: String, teacherId: Int) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.removeTeacher(teacherId)
        schoolRepository.save(school)
    }

    @Throws(SchoolNotFoundException::class)
    fun getSchool(id: String): School? {
        val foundSchool = schoolRepository.findById(id)
        return if (foundSchool.isPresent)
            foundSchool.get()
        else
            throw SchoolNotFoundException(id)
    }

    @Throws(StudentNotFoundException::class)
    fun getStudent(studentId: Int): Student? {
        val student = schoolRepository.findAll().flatMap { x -> x.students }
            .find { student: Student -> student.id == studentId }

        return student ?: throw StudentNotFoundException(studentId.toString())

    }

    @Throws(TeacherNotFoundException::class)
    fun getTeacher(teacherId: Int): Teacher? {
        val teacher = schoolRepository.findAll().flatMap { x -> x.teachers }
            .find { teacher: Teacher -> teacher.id == teacherId }

        return teacher ?: throw TeacherNotFoundException(teacherId.toString())

    }

    @Transactional
    fun removeSchool(id: String): Boolean {
        val unregistered = getSchool(id)?.unregister()
        if (unregistered == true)
            schoolRepository.deleteById(id)
        return true
    }
}