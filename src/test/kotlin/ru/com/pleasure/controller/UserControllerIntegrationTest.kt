package ru.com.pleasure.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier
import ru.com.pleasure.config.AbstractContainerTest
import ru.com.pleasure.service.UserService
import ru.pleasure.dto.RequestUser

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest : AbstractContainerTest() {

    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `test user registration`() {
        val requestUser = RequestUser().apply {
            username = "testUsername"
            surname = "testSurname"
            password = "testPassword"
        }

        StepVerifier.create(userService.register(requestUser))
            .expectSubscription()
            .verifyComplete()
    }

    @Test
    fun `test user login 200`() {
        val requestUser = RequestUser().apply {
            username = "testUsername"
            surname = "testSurname"
            password = "testPassword"
        }

        userService.register(requestUser).block()

        webTestClient.post()
            .uri("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestUser)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
            .expectBody(String::class.java)
            .value { token ->
                assert(token != null)
            }
    }

    @Test
    fun `test user login 401`() {
        val requestUser = RequestUser().apply {
            username = "testUsername"
            surname = "testSurname"
            password = "testPassword"
        }

        userService.register(requestUser).block()

        webTestClient.post()
            .uri("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestUser)
            .exchange()
            .expectStatus().isUnauthorized
    }

}