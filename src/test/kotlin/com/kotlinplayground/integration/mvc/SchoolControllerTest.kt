package com.kotlinplayground.integration.mvc

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.matchers.JsonPathMatchers
import com.kotlinplayground.domain.School
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.binder.test.InputDestination
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
@Import(TestChannelBinderConfiguration::class)
class SchoolControllerTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext
    lateinit var mockMvc: MockMvc

    @Autowired
    private val inputDestination: InputDestination? = null

    @Autowired
    private val outputDestination: OutputDestination? = null

    init {
        mongoDBContainer.withNetworkAliases("mongo")
            .withExposedPorts(27017).start()
    }

    companion object {
        private val mongoDBContainer = MongoDBContainer("mongo:6.0.4")
    }

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun whenAValidSchoolRequest_thenAddSchool() {
        val school = School("1", "noName")

        mockMvc!!.perform(
            post("/schools")
                .contentType("application/json")
                .param("sendWelcomeMail", "true")
                .content(objectMapper!!.writeValueAsString(school))
        )
            .andExpect(status().isAccepted)

        val received = outputDestination!!.receive(2000, "internal.education.events.schools")
        val payload = String(received.payload)
        MatcherAssert.assertThat(
            payload,
            JsonPathMatchers.hasJsonPath("$.id", Matchers.notNullValue())
        )
    }
}