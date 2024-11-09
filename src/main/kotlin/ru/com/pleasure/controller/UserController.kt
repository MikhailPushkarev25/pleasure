package ru.com.pleasure.controller

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.com.pleasure.service.JWTUtil
import ru.com.pleasure.service.UserService
import ru.pleasure.dto.RequestUser

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val jwtUtil: JWTUtil
) {

    @PostMapping("/login")
    suspend fun login(@RequestBody request: RequestUser): ResponseEntity<String> {
        return userService.authenticate(request.username, request.password)
            .map { userDetails ->
                val token = jwtUtil.generateToken(userDetails)
                ResponseEntity.ok(token)
            }
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials")))
            .awaitSingle()
    }


    @PostMapping("/register")
    suspend fun register(@RequestBody request: RequestUser): ResponseEntity<String> {
        return try {
            userService.register(request).awaitSingle()
            ResponseEntity.ok("User registered successfully")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed")
        }
    }
}