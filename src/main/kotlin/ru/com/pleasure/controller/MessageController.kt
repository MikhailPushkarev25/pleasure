package ru.com.pleasure.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.com.pleasure.entity.Attachment
import ru.com.pleasure.entity.Message
import ru.com.pleasure.service.MessageService
import ru.pleasure.dto.MessageDto

@RestController
@RequestMapping("api/chat")
class MessageController(
    private val chatService: MessageService
) {

    @PostMapping("/{chatId}/messages")
    suspend fun sendMessage(
        @PathVariable chatId: Long,
        @RequestParam userId: Long,
        @RequestParam content: String,
        @RequestParam(required = false) attachments: List<FilePart>?
    ): Mono<ResponseEntity<Message>> {
        return chatService.sendMessage(chatId, userId, content, attachments)
            .map { message -> ResponseEntity(message, HttpStatus.CREATED) }
            .onErrorResume { e ->
                when (e.message) {
                    "User not found" -> Mono.just(ResponseEntity(HttpStatus.NOT_FOUND))
                    "Chat not found" -> Mono.just(ResponseEntity(HttpStatus.NOT_FOUND))
                    else -> Mono.just(ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR))
                }
            }
    }

    @PostMapping("/{chatId}/messages/{messageId}/attachments")
    suspend fun uploadAttachment(
        @PathVariable chatId: Long,
        @PathVariable messageId: Long,
        @RequestPart attachment: FilePart
    ): Mono<ResponseEntity<Attachment>> {
        return chatService.uploadAttachment(messageId, attachment)
            .map { attachment -> ResponseEntity(attachment, HttpStatus.CREATED) }
            .onErrorResume { e ->
                Mono.just(ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR))
            }
    }

    @GetMapping("/{chatId}/messages")
    suspend fun getMessages(@PathVariable chatId: Long): ResponseEntity<Flux<Message>> {
        val messages = chatService.getMessages(chatId)
        return ResponseEntity.ok(messages)
    }
}