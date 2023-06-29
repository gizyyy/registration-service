package com.kotlinplayground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinBasicApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinBasicApplication>(*args)
}

