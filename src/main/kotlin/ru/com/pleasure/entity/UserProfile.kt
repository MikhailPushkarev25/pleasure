package ru.com.pleasure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("user_profile")
data class UserProfile(
    @Id
    @Column("user_id")
    val userId: Long,
    @Column("avatar_url")
    val avatarUrl: String? = null,
    val bio: String? = null
)