package com.kotlinplayground.application.exceptions

import java.lang.RuntimeException

class SchoolNotFoundException(id: String) : RuntimeException(id) {

}