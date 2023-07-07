package com.kotlinplayground.infrastructure.producer

import com.kotlinplayground.domain.externalevents.SchoolRegisteredEvent
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.UUID.randomUUID

@Service
class ExternalSchoolProducer(private val streamBridge: StreamBridge) {

    @Scheduled(cron = "*/10 * * * * *")
    fun sendExternal() {
        val event = SchoolRegisteredEvent(
            "1",
            "adsiz",
            arrayListOf(),
            arrayListOf()
        )

        val randomUUID = randomUUID()
        var headersMap = mutableMapOf<String, Any>()
        headersMap["ce_type"] = SchoolRegisteredEvent.type
        headersMap["partitionKey"] = event.schoolId

        var headers = MessageHeaders(headersMap)
        val message = MessageBuilder.createMessage(event, headers)


        streamBridge.send("external.education.events.schools", message);

    }
}