package ru.com.pleasure.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import ru.com.pleasure.entity.User

interface UserRepository : ReactiveCrudRepository<User, Long> {

    fun findByUsernameAndSurname(username: String, surname: String): Mono<User>

    fun findByUsername(username: String): Mono<User>
}