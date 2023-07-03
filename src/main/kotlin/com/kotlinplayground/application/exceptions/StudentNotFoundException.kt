package com.kotlinplayground.application.exceptions

import java.lang.RuntimeException

class StudentNotFoundException(id: String) : RuntimeException(id) {

}