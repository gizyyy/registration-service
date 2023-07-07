package com.kotlinplayground.infrastructure.config

import org.springframework.cloud.function.context.MessageRoutingCallback
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

@Component
class RoutingConfig {
    @Bean
    fun messageRoutingCallback(): MessageRoutingCallback {
        return object : MessageRoutingCallback {
            override fun routingResult(message: Message<*>): String {
                return RouteBinding.findBinding(message.headers["ce_type"].toString())
            }
        }
    }

}