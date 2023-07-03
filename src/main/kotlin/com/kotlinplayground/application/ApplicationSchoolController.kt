package com.kotlinplayground.application

import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.Teacher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ApplicationSchoolController(private val applicationService: ApplicationService) {

    @GetMapping("/schools/{id}")
    fun getSchool(@PathVariable id: String): ResponseEntity<School> {
        return ResponseEntity.ok(applicationService.getSchool(id))
    }

    @PostMapping("/schools")
    fun addOrChangeSchool(@RequestBody school: School
    ): ResponseEntity<Any> {
        applicationService.addSchool(school)
        return ResponseEntity.accepted().build()
    }

    @DeleteMapping("/schools/{id}")
    fun deleteSchool(@PathVariable id: String): ResponseEntity<Any> {
        applicationService.removeSchool(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/schools/{schoolId}/teachers/{teacherId}")
    fun changeTeacher(
        @PathVariable schoolId: String, @RequestBody teacher: Teacher
    ): ResponseEntity<Any> {
        applicationService.addTeacherToSchool(schoolId,teacher)
        return ResponseEntity.accepted().build()
    }

    @DeleteMapping("/schools/{schoolId}/students/{studentId}")
    fun deleteStudent(
        @PathVariable schoolId: String,
        @PathVariable studentId: Int
    ): ResponseEntity<Any> {
        applicationService.removeStudentFromSchool(schoolId, studentId)
        return ResponseEntity.noContent().build()
    }

}