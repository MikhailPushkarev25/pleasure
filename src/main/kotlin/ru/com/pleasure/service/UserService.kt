package ru.com.pleasure.service

import mu.KotlinLogging
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.com.pleasure.entity.User
import ru.com.pleasure.mapping.toEntity
import ru.com.pleasure.repository.UserRepository
import ru.pleasure.dto.RequestUser
import ru.pleasure.dto.UserDto
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    val log = KotlinLogging.logger {  }

    fun authenticate(username: String, password: String): Mono<UserDetails> {
        log.info("Searching for user: $username")
        return userRepository.findByUsername(username)
            .doOnSubscribe { log.info("User found: $username") }
            .switchIfEmpty(Mono.error(UsernameNotFoundException("User not found for username: $username")))
            .flatMap { user ->
                if (!passwordEncoder.matches(password, user.password)) {
                    Mono.error(BadCredentialsException("Invalid password"))
                } else {
                    Mono.just(
                        org.springframework.security.core.userdetails.User.withUsername(user.username)
                            .password(user.password)
                            .authorities(emptyList())
                            .build()
                    )
                }
            }
    }

    fun register(request: RequestUser): Mono<Void> {
        val newUser = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            surname = request.surname,
        )
        return userRepository.save(newUser)
            .doOnError { log.error("Error saving user: ${request.username}", it) }
            .then()
    }
}