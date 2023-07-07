package com.kotlinplayground.infrastructure.producer

import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.Student
import com.kotlinplayground.domain.Teacher
import com.kotlinplayground.domain.externalevents.SchoolRegisteredEvent
import com.kotlinplayground.domain.externalevents.StudentRegisteredEvent
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID.randomUUID

@Service
class ExternalSchoolProducer(private val streamBridge: StreamBridge) {

    @Scheduled(cron = "*/20 * * * * *")
    fun sendExternal() {
        val event = SchoolRegisteredEvent(
            School(
                "1",
                "adsiz",
                arrayListOf(),
                arrayListOf()
            )
        )

        val randomUUID = randomUUID()
        var headersMap = mutableMapOf<String, Any>()
        headersMap["ce_type"] = SchoolRegisteredEvent.type
        headersMap["partitionKey"] = event.school.schoolId

        var headers = MessageHeaders(headersMap)
        val message = MessageBuilder.createMessage(event, headers)


        streamBridge.send("external.education.events.schools", message);

    }

    @Scheduled(cron = "*/20 * * * * *")
    fun sendExternalStudent() {
        val event = StudentRegisteredEvent(
            "1",
            Student("hede", "add", 2, arrayListOf<Int>()),
            Instant.now()
        )

        val randomUUID = randomUUID()
        var headersMap = mutableMapOf<String, Any>()
        headersMap["ce_type"] = StudentRegisteredEvent.type
        headersMap["partitionKey"] = event.student.id

        var headers = MessageHeaders(headersMap)
        val message = MessageBuilder.createMessage(event, headers)

        streamBridge.send("external.education.events.students", message);

    }
}