package ru.com.pleasure.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.com.pleasure.entity.Chat

interface ChatRepository : ReactiveCrudRepository<Chat, Long>