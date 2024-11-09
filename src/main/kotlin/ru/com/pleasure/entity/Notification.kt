package ru.com.pleasure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("notifications")
data class Notification(
    @Id
    val id: Long? = null,
    @Column("user_id")
    val userId: Long,
    val message: String,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)