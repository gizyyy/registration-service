package com.kotlinplayground.application

import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.Teacher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ApplicationTeacherController(private val applicationService: ApplicationService) {

    @GetMapping("/teachers/{id}")
    fun getTeacher(@PathVariable id: Int): ResponseEntity<Teacher> {
        return ResponseEntity.ok(applicationService.getTeacher(id))
    }

    @PutMapping("/teachers/{schoolId}")
    fun addTeacher(
        @PathVariable schoolId: String, @RequestBody teacher: Teacher
    ): ResponseEntity<Any> {
        applicationService.addTeacherToSchool(schoolId,teacher)
        return ResponseEntity.accepted().build()
    }
}