package com.kotlinplayground.infrastructure.events.school

import com.kotlinplayground.infrastructure.events.school.SchoolEvent

class SchoolRemovedEvent(override var schoolId: String) :
    SchoolEvent