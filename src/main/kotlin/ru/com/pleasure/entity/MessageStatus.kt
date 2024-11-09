package ru.com.pleasure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("message_status")
data class MessageStatus(
    @Id
    val id: Long? = null,
    @Column("message_id")
    val messageId: Long,
    val status: String,
    @Column("timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now()
)