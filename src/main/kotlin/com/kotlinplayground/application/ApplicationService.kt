package com.kotlinplayground.application

import com.kotlinplayground.application.exceptions.SchoolNotFoundException
import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.Student
import com.kotlinplayground.domain.Teacher
import com.kotlinplayground.infrastructure.repositories.SchoolRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApplicationService(private val schoolRepository: SchoolRepository) {

    @Transactional
    fun addSchool(school: School): Boolean {
        school.register()
        schoolRepository.save(school)
        return true
    }

    @Transactional
    fun addTeacherToSchool(schoolId: String, teacher: Teacher) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.addTeacher(teacher)
        schoolRepository.save(school)
    }

    @Transactional
    fun addStudentToSchool(schoolId: String, student: Student) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.addStudent(student)
        schoolRepository.save(school)
    }

    @Transactional
    fun registerStudentToTeacher(schoolId: String, student: Student) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.addStudent(student)
        schoolRepository.save(school)
    }

    @Transactional
    fun removeStudentFromSchool(schoolId: String, studentId: Int) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.removeStudent(studentId)
        schoolRepository.save(school)
    }

    @Transactional
    fun removeTeacherFromSchool(schoolId: String, teacherId: Int) {
        val foundSchool = schoolRepository.findById(schoolId)
        if (foundSchool.isEmpty)
            throw SchoolNotFoundException(schoolId)

        val school = foundSchool.get()
        school.removeTeacher(teacherId)
        schoolRepository.save(school)
    }

    fun getSchool(id: String): School? {
        val foundSchool = schoolRepository.findById(id)
        return if (foundSchool.isPresent)
            foundSchool.get()
        else
            null
    }

    fun getStudent(schoolId: String, studentId: Int): Student? {
        val foundSchool = schoolRepository.findById(schoolId)
        return if (foundSchool.isPresent)
            foundSchool.get().students.find { x: Student -> x.id == studentId }
        else
            null
    }

    @Transactional
    fun removeSchool(id: String): Boolean {
        val unregistered = getSchool(id)?.unregister()
        if (unregistered == true)
            schoolRepository.deleteById(id)
        return true
    }
}