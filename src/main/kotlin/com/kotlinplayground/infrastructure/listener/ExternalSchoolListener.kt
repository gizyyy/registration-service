package com.kotlinplayground.infrastructure.listener

import com.kotlinplayground.application.ApplicationService
import com.kotlinplayground.domain.externalevents.SchoolDeletedEvent
import com.kotlinplayground.domain.externalevents.SchoolRegisteredEvent
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
import java.util.function.Consumer


@Component
class ExternalSchoolListener(
    private val messageRoutingCallback: MessageRoutingCallback,
    private val applicationService: ApplicationService
) {

    @Bean
    fun schoolEventsRouter(
        functionCatalog: FunctionCatalog?, functionProperties: FunctionProperties,
        beanFactory: BeanFactory, routingCallback: MessageRoutingCallback?
    ): RoutingFunction? {
        return RoutingFunction(
            functionCatalog, functionProperties, BeanFactoryResolver(beanFactory),
            messageRoutingCallback
        )
    }

    @Bean
    fun consumeSchoolDeletedEvent(): Consumer<Message<SchoolDeletedEvent>> {
        return Consumer<Message<SchoolDeletedEvent>> { schoolRegisteredEvent: Message<SchoolDeletedEvent> ->
            val event = schoolRegisteredEvent.payload
            logger.info { "SchoolDeletedEvent will be processed. Event: $event" }
            applicationService.removeSchool(event.schoolId)
        }
    }

    @Bean
    fun consumeSchoolRegisteredEvent(): Consumer<Message<SchoolRegisteredEvent>> {
        return Consumer<Message<SchoolRegisteredEvent>> { schoolRegisteredEvent: Message<SchoolRegisteredEvent> ->
            val event = schoolRegisteredEvent.payload
            logger.info { "SchoolRegisteredEvent will be processed. Event: $event" }
            applicationService.addSchool(event.school)
        }
    }


    companion object {
        val logger = KotlinLogging.logger { }
    }


}