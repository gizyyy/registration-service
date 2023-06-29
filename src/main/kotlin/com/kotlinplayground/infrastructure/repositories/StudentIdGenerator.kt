package com.kotlinplayground.infrastructure.repositories

import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class StudentIdGenerator() {
    companion object {
        var counter: AtomicInteger = AtomicInteger(0)
        fun generate(): Int = counter.incrementAndGet()
    }
}