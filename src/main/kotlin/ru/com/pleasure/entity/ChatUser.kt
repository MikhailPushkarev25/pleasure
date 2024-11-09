package ru.com.pleasure.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("chat_user")
data class ChatUser(
    @Column("chat_id")
    val chatId: Long,
    @Column("user_id")
    val userId: Long,
    val role: String = "member"
)