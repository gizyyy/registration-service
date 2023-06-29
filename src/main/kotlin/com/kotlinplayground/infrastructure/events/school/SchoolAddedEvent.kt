package com.kotlinplayground.infrastructure.events.school

import com.kotlinplayground.infrastructure.events.school.SchoolEvent

class SchoolAddedEvent(override var schoolId: String) :
    SchoolEvent