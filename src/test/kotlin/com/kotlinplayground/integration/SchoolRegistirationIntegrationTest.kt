package com.kotlinplayground.integration

import com.jayway.jsonpath.matchers.JsonPathMatchers
import com.kotlinplayground.domain.School
import com.kotlinplayground.domain.externalevents.SchoolRegisteredEvent
import com.kotlinplayground.infrastructure.repositories.SchoolRepository
import junit.framework.TestCase.*
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cloud.stream.binder.test.InputDestination
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration
import org.springframework.context.annotation.Import
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.IOException

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
@Import(TestChannelBinderConfiguration::class)
class SchoolRegistirationIntegrationTest {

    @Autowired
    private val inputDestination: InputDestination? = null

    @Autowired
    private val outputDestination: OutputDestination? = null

    @Autowired
    private val schoolRepository: SchoolRepository? = null

    init {
        mongoDBContainer.withNetworkAliases("mongo")
            .withExposedPorts(27017).start()
    }

    companion object {
        private val mongoDBContainer = MongoDBContainer("mongo:6.0.4")
    }

    @Test
    @Throws(IOException::class)
    fun whenSchoolRegisteredEventSentInternalEventShouldBeEmitted() {
        val schoolRegisteredEvent = SchoolRegisteredEvent(
            School(
                "1",
                "noName",
                arrayListOf(),
                arrayListOf()
            )
        )
        val headersMap = mutableMapOf<String, Any>()
        headersMap["ce_type"] = SchoolRegisteredEvent.type
        headersMap["partitionKey"] = schoolRegisteredEvent.school.schoolId
        val headers = MessageHeaders(headersMap)

        inputDestination!!.send(
            MessageBuilder.createMessage(schoolRegisteredEvent, headers),
            "external.education.events.schools"
        )

        // Asserting emitted event
        val received = outputDestination!!.receive(2000, "internal.education.events.schools")
        val payload = String(received.payload)
        MatcherAssert.assertThat(
            payload,
            JsonPathMatchers.hasJsonPath("$.id", Matchers.notNullValue())
        )
        assertEquals(
            received.headers["ce_type"],
            "education.service.events.external.schools.SchoolRegisteredEvent"
        )
        assertEquals(
            received.headers["contentType"],
            "application/json"
        )
        MatcherAssert.assertThat(
            payload,
            JsonPathMatchers.hasJsonPath("$.schoolName", Matchers.equalTo("noName"))
        )

        assertNotNull(schoolRepository!!.findById("1"))
    }

}