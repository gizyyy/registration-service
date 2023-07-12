package com.kotlinplayground.infrastructure.producer

import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.Student
import com.kotlinplayground.domain.externalevents.SchoolRegisteredEvent
import com.kotlinplayground.domain.externalevents.StudentRegisteredEvent
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.function.Supplier

@Service
class ExternalSchoolProducer {

    @Bean
    fun schoolExternalEventSupplier(): Supplier<Message<SchoolRegisteredEvent>> {
        return Supplier<Message<SchoolRegisteredEvent>> {
            val schoolRegisteredEvent = SchoolRegisteredEvent(
                School(
                    "1",
                    "adsiz",
                    arrayListOf(),
                    arrayListOf()
                )
            )
            val headersMap = mutableMapOf<String, Any>()
            headersMap["ce_type"] = SchoolRegisteredEvent.type
            headersMap["partitionKey"] = schoolRegisteredEvent.school.schoolId
            logger.info { "SchoolRegisteredEvent will be sent. Event: $schoolRegisteredEvent" }
            val headers = MessageHeaders(headersMap)
            MessageBuilder.createMessage(schoolRegisteredEvent, headers)
        }
    }
    @Bean
    fun studentExternalEventSupplier(): Supplier<Message<StudentRegisteredEvent>> {
        return Supplier<Message<StudentRegisteredEvent>> {

            val event = StudentRegisteredEvent(
                "1",
                Student("hede", "add", 2, arrayListOf<Int>()),
                Instant.now()
            )
            logger.info { "StudentRegisteredEvent will be sent. Event: $event" }

            val headersMap = mutableMapOf<String, Any>()
            headersMap["ce_type"] = StudentRegisteredEvent.type
            headersMap["partitionKey"] = event.student.id

            val headers = MessageHeaders(headersMap)
            MessageBuilder.createMessage(event, headers)
        }
    }

    companion object {
        val logger = KotlinLogging.logger { }
    }

}