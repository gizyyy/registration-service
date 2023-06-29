package com.kotlinplayground.infrastructure.repositories

import com.kotlinplayground.domain.School
import org.springframework.data.mongodb.repository.MongoRepository

interface SchoolRepository : MongoRepository<School, String>