package ru.com.pleasure.service

import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.com.pleasure.entity.Attachment
import ru.com.pleasure.entity.Message
import ru.com.pleasure.repository.AttachmentRepository
import ru.com.pleasure.repository.ChatRepository
import ru.com.pleasure.repository.MessageRepository
import ru.com.pleasure.repository.UserRepository
import java.time.LocalDateTime

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val fileStorageService: FileStorageService,
    private val attachmentRepository: AttachmentRepository
) {

    fun sendMessage(chatId: Long, userId: Long, content: String, attachments: List<FilePart>?): Mono<Message> {
        val message = Message(userId = userId, chatId = chatId, content = content, createdAt = LocalDateTime.now())

        return userRepository.findById(userId)
            .flatMap { user ->
                chatRepository.findById(chatId)
                    .flatMap { chat ->
                        messageRepository.save(message).flatMap { savedMessage ->
                            attachments?.let { files ->
                                Flux.fromIterable(files)
                                    .flatMap { file ->
                                        fileStorageService.storeFile(file).flatMap { filePath ->
                                            val attachment = Attachment(
                                                messageId = savedMessage.id!!,
                                                filePath = filePath,
                                                fileType = file.headers().contentType.toString()
                                            )
                                            attachmentRepository.save(attachment)
                                        }
                                    }
                                    .collectList()
                                    .then(Mono.just(savedMessage))
                            } ?: Mono.just(savedMessage)
                        }
                    }
                    .switchIfEmpty(Mono.error(Exception("Chat not found")))
            }
            .switchIfEmpty(Mono.error(Exception("User not found")))
    }

    fun getMessages(chatId: Long): Flux<Message> {
        return messageRepository.findByChatId(chatId)
    }

    fun uploadAttachment(messageId: Long, attachment: FilePart): Mono<Attachment> {
        return createAttachmentFromFilePart(messageId, attachment)
            .flatMap { attachmentObj ->
                attachmentRepository.save(attachmentObj)
            }
    }

    private fun createAttachmentFromFilePart(messageId: Long, filePart: FilePart): Mono<Attachment> {
        return fileStorageService.storeFile(filePart).map { filePath ->
            Attachment(
                messageId = messageId,
                filePath = filePath,
                fileType = filePart.headers().contentType.toString()
            )
        }
    }
}