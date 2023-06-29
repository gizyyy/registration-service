package com.kotlinplayground.domain

class Teacher(var id: Int, var name: String, var address: String, val branch: Branch)

enum class Branch {
    MATH, PHYSICS, LITERATURE, ENGLISH, SPORT
}