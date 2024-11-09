package ru.com.pleasure.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("attachments")
data class Attachment(
    @Id
    val id: Long? = null,
    @Column("message_id")
    val messageId: Long,
    @Column("file_path")
    val filePath: String,
    @Column("file_type")
    val fileType: String
)