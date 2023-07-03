package com.kotlinplayground.infrastructure.listener


import com.kotlinplayground.domain.domainevents.ApplicationDomainEvent
import com.kotlinplayground.domain.domainevents.school.SchoolAddedEvent
import org.springframework.data.domain.DomainEvents
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DomainEventListener {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun notify(applicationDomainEvent: ApplicationDomainEvent) {
        val integrationEvent = applicationDomainEvent.convertToIntegrationEvent()
        //convert to integration event
        //emit integration event
    }

}