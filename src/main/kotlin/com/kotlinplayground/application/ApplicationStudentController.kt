package com.kotlinplayground.application

import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.Student
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ApplicationStudentController(private val applicationService: ApplicationService) {

    @GetMapping("/students/{id}")
    fun getStudent(@PathVariable id: Int): ResponseEntity<Student> {
        return ResponseEntity.ok(applicationService.getStudent(id))
    }

    @PutMapping("/students/{schoolId}")
    fun addStudent(
        @PathVariable schoolId: String, @RequestBody student: Student
    ): ResponseEntity<Any> {
        applicationService.addStudentToSchool(schoolId, student)
        return ResponseEntity.accepted().build()
    }

    @PostMapping("/students/{schoolId}")
    fun changeStudent(@PathVariable schoolId:String, @RequestBody student: Student
    ): ResponseEntity<Any> {
        applicationService.changeStudentData(student, schoolId)
        return ResponseEntity.accepted().build()
    }



}