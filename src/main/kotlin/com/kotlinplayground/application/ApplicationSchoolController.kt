package com.kotlinplayground.application

import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.Teacher
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Duration

@RestController
class ApplicationSchoolController(private val applicationService: ApplicationService) {

    private lateinit var bucket: Bucket

    init {
        val limit = Bandwidth.simple(20, Duration.ofMinutes(1L))
        bucket = Bucket.builder().addLimit(limit).build()
    }

    @GetMapping("/schools/{id}")
    fun getSchool(@PathVariable id: String): ResponseEntity<School> {
        return ResponseEntity.ok(applicationService.getSchool(id))
    }

    @PostMapping("/schools")
    fun addOrChangeSchool(
        @RequestBody school: School
    ): ResponseEntity<Any> {
        if (bucket.tryConsume(1)) {
            applicationService.addSchool(school)
            return ResponseEntity.accepted().build()
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @DeleteMapping("/schools/{id}")
    fun deleteSchool(@PathVariable id: String): ResponseEntity<Any> {
        applicationService.removeSchool(id)
        return ResponseEntity.noContent().build()
    }

}