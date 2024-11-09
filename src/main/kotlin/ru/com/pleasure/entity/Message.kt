package ru.com.pleasure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("messages")
data class Message(
    @Id
    val id: Long? = null,
    @Column("user_id")
    val userId: Long,
    @Column("chat_id")
    val chatId: Long,
    val content: String,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)