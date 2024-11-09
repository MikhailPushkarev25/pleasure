package ru.com.pleasure.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import ru.com.pleasure.entity.Message

interface MessageRepository : ReactiveCrudRepository<Message, Long> {
    fun findByChatId(chatId: Long): Flux<Message>
}