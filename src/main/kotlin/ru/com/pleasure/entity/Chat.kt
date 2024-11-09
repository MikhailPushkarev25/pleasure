package ru.com.pleasure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("chats")
data class Chat(
    @Id
    val id: Long? = null,
    val name: String? = null,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)