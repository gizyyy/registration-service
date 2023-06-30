package com.kotlinplayground.infrastructure.listener


import com.kotlinplayground.domain.domainevents.school.SchoolAddedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DomainEventListener {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun notify(schoolAddedEvent: SchoolAddedEvent) {
        val integrationEvent = schoolAddedEvent.convertToIntegrationEvent()
        //convert to integration event
        //emit integration event
    }

}