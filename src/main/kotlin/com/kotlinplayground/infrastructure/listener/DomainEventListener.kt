package com.kotlinplayground.infrastructure.listener


import com.kotlinplayground.domain.domainevents.ApplicationDomainEvent
import com.kotlinplayground.domain.domainevents.school.SchoolAddedEvent
import com.kotlinplayground.domain.domainevents.school.SchoolEvent
import com.kotlinplayground.domain.domainevents.student.StudentEvent
import com.kotlinplayground.domain.domainevents.teacher.TeacherEvent
import com.kotlinplayground.domain.externalevents.SchoolRegisteredEvent
import mu.KotlinLogging
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.data.domain.DomainEvents
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DomainEventListener(private val streamBridge: StreamBridge) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun notify(applicationDomainEvent: ApplicationDomainEvent) {
        val integrationEvent = applicationDomainEvent.convertToIntegrationEvent()
        var headersMap = mutableMapOf<String, Any>()
        headersMap["ce_type"] = SchoolRegisteredEvent.type

        var target: String = ""
        if (applicationDomainEvent is StudentEvent) {
            target = "studentEventsRouter-out-0"
        }
        if (applicationDomainEvent is SchoolEvent)
            target = "schoolEventsRouter-out-0"

        if (applicationDomainEvent is TeacherEvent)
            target = "teacherEventsRouter-out-0"

        headersMap["partitionKey"] = integrationEvent.id
        var headers = MessageHeaders(headersMap)
        val message = MessageBuilder.createMessage(integrationEvent, headers)

        logger.info { "IntegrationEvent will be processed. Event: $message" }
        streamBridge.send(target, message);

        //convert to integration event
        //emit integration event
    }

    companion object {
        val logger = KotlinLogging.logger { }
    }

}