package com.kotlinplayground.application.exceptions

import java.lang.RuntimeException

class TeacherNotFoundException(id: String) : RuntimeException(id) {

}