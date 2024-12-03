package ru.com.pleasure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id
    val id: Long? = null,
    @Column("full_name")
    val fullName: String,
    val email: String,
    val password: String,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)