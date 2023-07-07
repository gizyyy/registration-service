package com.kotlinplayground.infrastructure.listener

import com.kotlinplayground.domain.externalevents.SchoolRegisteredEvent
import mu.KotlinLogging
import org.springframework.cloud.function.context.MessageRoutingCallback
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.function.Consumer


@Component
class ExternalSchoolListener {

    @Bean
    fun messageRoutingCallback(): MessageRoutingCallback {
        return object : MessageRoutingCallback {
            override fun routingResult(message: Message<*>): String {
                return RouteBinding.findBinding(message.headers["ce_type"].toString())
            }
        }
    }

    @Bean
    fun school(): Consumer<Message<SchoolRegisteredEvent>> {
        return Consumer<Message<SchoolRegisteredEvent>> { schoolRegisteredEvent: Message<SchoolRegisteredEvent> ->

            logger.info { "Event will be processed. event: $schoolRegisteredEvent" }

        }
    }


    companion object {
        val logger = KotlinLogging.logger { }
    }


}