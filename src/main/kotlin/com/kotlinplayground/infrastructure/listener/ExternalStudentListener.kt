package com.kotlinplayground.infrastructure.listener

import com.kotlinplayground.application.ApplicationService
import com.kotlinplayground.domain.domainevents.ApplicationDomainEvent
import com.kotlinplayground.domain.externalevents.SchoolRegisteredEvent
import com.kotlinplayground.domain.externalevents.StudentDeletedEvent
import com.kotlinplayground.domain.externalevents.StudentRegisteredEvent
import mu.KotlinLogging
import org.springframework.beans.factory.BeanFactory
import org.springframework.cloud.function.context.FunctionCatalog
import org.springframework.cloud.function.context.FunctionProperties
import org.springframework.cloud.function.context.MessageRoutingCallback
import org.springframework.cloud.function.context.config.RoutingFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.expression.BeanFactoryResolver
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.function.Consumer


@Component
class ExternalStudentListener(
    private val messageRoutingCallback: MessageRoutingCallback,
    private val applicationService: ApplicationService
) {

    @Bean
    fun studentEventsRouter(
        functionCatalog: FunctionCatalog?, functionProperties: FunctionProperties,
        beanFactory: BeanFactory, routingCallback: MessageRoutingCallback?
    ): RoutingFunction? {
        return RoutingFunction(
            functionCatalog, functionProperties, BeanFactoryResolver(beanFactory),
            messageRoutingCallback
        )
    }

    @Bean
    fun consumeStudentRegisteredEvent(): Consumer<Message<StudentRegisteredEvent>> {
        return Consumer<Message<StudentRegisteredEvent>> { schoolRegisteredEvent: Message<StudentRegisteredEvent> ->
            val event = schoolRegisteredEvent.payload
            logger.info { "StudentRegisteredEvent will be processed. Event: $event" }
            applicationService.addStudentToSchool(
                event.schoolId,
                event.student
            )
        }
    }

    @Bean
    fun consumeStudentDeletedEvent(): Consumer<Message<StudentDeletedEvent>> {
        return Consumer<Message<StudentDeletedEvent>> { studentDeletedEvent: Message<StudentDeletedEvent> ->
            val event = studentDeletedEvent.payload
            logger.info { "StudentDeletedEvent will be processed. Event: $event" }
            applicationService.removeStudentFromSchool(
                event.schoolId,
                event.studentId
            )
        }
    }


    companion object {
        val logger = KotlinLogging.logger { }
    }


}