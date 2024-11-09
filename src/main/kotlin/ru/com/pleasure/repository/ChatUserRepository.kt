package ru.com.pleasure.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.com.pleasure.entity.ChatUser

interface ChatUserRepository : ReactiveCrudRepository<ChatUser, Long> {
    fun findByChatId(chatId: Long): Flux<ChatUser>
    fun findByUserId(userId: Long): Flux<ChatUser>
    fun deleteByChatIdAndUserId(chatId: Long, userId: Long): Mono<Int>
}