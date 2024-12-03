package ru.com.pleasure.service

import mu.KotlinLogging
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.com.pleasure.entity.User
import ru.com.pleasure.repository.UserRepository
import ru.pleasure.dto.RequestUser

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    val log = KotlinLogging.logger {  }

    fun authenticate(email: String, password: String): Mono<UserDetails> {
        log.info("Searching for user: $email")
        return userRepository.findByEmail(email)
            .doOnSubscribe { log.info("User found: $email") }
            .switchIfEmpty(Mono.error(UsernameNotFoundException("User not found for username: $email")))
            .flatMap { user ->
                if (!passwordEncoder.matches(password, user.password)) {
                    Mono.error(BadCredentialsException("Invalid password"))
                } else {
                    Mono.just(
                        org.springframework.security.core.userdetails.User.withUsername(user.fullName)
                            .password(user.password)
                            .authorities(emptyList())
                            .build()
                    )
                }
            }
    }

    fun register(request: RequestUser): Mono<Void> {
        val newUser = User(
            fullName = request.fullname,
            password = passwordEncoder.encode(request.password),
            email = request.email,
        )
        return userRepository.save(newUser)
            .doOnError { log.error("Error saving user: ${request.fullname}", it) }
            .then()
    }
}