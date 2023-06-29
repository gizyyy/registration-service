package com.kotlinplayground.infrastructure

import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class TeacherIdGenerator() {
    companion object {
        var counter: AtomicInteger = AtomicInteger(0)
        fun generate(): Int = counter.incrementAndGet()
    }
}