package com.kotlinplayground.domain

class Student(
    var name: String,
    var address: String,
    var id: Int,
    var registeredTeachers: ArrayList<Int> = arrayListOf()
)