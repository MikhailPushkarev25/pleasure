package ru.com.pleasure.controller

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.com.pleasure.entity.Chat
import ru.com.pleasure.entity.ChatUser
import ru.com.pleasure.service.ChatService
import ru.pleasure.dto.CreateChatRequest

@RestController
@RequestMapping("/api/chats")
class ChatController(private val chatService: ChatService) {

    val log = KotlinLogging.logger { }

    @PostMapping("/create")
    suspend fun createChat(@RequestBody request: CreateChatRequest): ResponseEntity<Chat> {
        log.info("Creating chat with request: $request")
        val chat = chatService.createChat(request.name)
        log.info("Chat created: $chat")
        return ResponseEntity(chat, HttpStatus.CREATED)
    }

    @GetMapping("/getChats")
    suspend fun getChats(): ResponseEntity<List<Chat>> {
        val chats = chatService.getChats()
        return ResponseEntity.ok(chats)
    }

    @GetMapping("/{chatId}")
    suspend fun getChatDetails(@PathVariable chatId: Long): ResponseEntity<Chat> {
        val chat = chatService.getChatDetails(chatId)
        return if (chat != null) {
            ResponseEntity.ok(chat)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{chatId}")
    suspend fun deleteChat(@PathVariable chatId: Long): ResponseEntity<Void> {
        chatService.deleteChat(chatId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{chatId}/users")
    suspend fun addUserToChat(
        @PathVariable chatId: Long,
        @RequestParam userId: Long,
        @RequestParam(required = false) role: String = "member"
    ): ResponseEntity<ChatUser> {
        val chatUser = chatService.addUserToChat(chatId, userId, role)
        return ResponseEntity(chatUser, HttpStatus.CREATED)
    }

    @DeleteMapping("/{chatId}/users/{userId}")
    suspend fun removeUserFromChat(
        @PathVariable chatId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<Void> {
        val removed = chatService.removeUserFromChat(chatId, userId)
        return if (removed) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{chatId}/users")
    suspend fun getUsersInChat(@PathVariable chatId: Long): ResponseEntity<List<ChatUser>> {
        val users = chatService.getUsersInChat(chatId)
        return ResponseEntity.ok(users)
    }
}