package ru.com.pleasure.service

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.com.pleasure.entity.Chat
import ru.com.pleasure.entity.ChatUser
import ru.com.pleasure.repository.ChatRepository
import ru.com.pleasure.repository.ChatUserRepository
import ru.com.pleasure.repository.MessageRepository

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val chatUserRepository: ChatUserRepository,
) {

    suspend fun createChat(name: String): Chat {
        val chat = Chat(name = name)
        return chatRepository.save(chat).awaitSingle()
    }

    suspend fun getChats(): List<Chat> {
        return chatRepository.findAll().collectList().awaitSingle()
    }

    suspend fun getChatDetails(chatId: Long): Chat? {
        return chatRepository.findById(chatId).awaitFirstOrNull()
    }

    suspend fun deleteChat(chatId: Long) {
        val exists = chatRepository.existsById(chatId).awaitSingleOrNull()
        if (!exists!!) {
            throw Exception("Chat with ID $chatId not found.")
        }
        chatRepository.deleteById(chatId).awaitSingleOrNull()
    }
    suspend fun addUserToChat(chatId: Long, userId: Long, role: String = "member"): ChatUser? {
        val chatUser = ChatUser(chatId = chatId, userId = userId, role = role)
        return chatUserRepository.save(chatUser).awaitSingleOrNull()
    }

    suspend fun removeUserFromChat(chatId: Long, userId: Long): Boolean {
        return chatUserRepository.deleteByChatIdAndUserId(chatId, userId).awaitSingle() > 0
    }

    suspend fun getUsersInChat(chatId: Long): List<ChatUser> {
        return chatUserRepository.findByChatId(chatId).collectList().awaitSingle()
    }
}