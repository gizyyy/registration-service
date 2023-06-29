package com.kotlinplayground.application

import com.kotlinplayground.domain.School
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ApplicationController(private val applicationService: ApplicationService) {

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


}