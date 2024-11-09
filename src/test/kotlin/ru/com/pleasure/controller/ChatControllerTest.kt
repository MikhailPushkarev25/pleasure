package ru.com.pleasure.controller

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import ru.com.pleasure.config.AbstractContainerTest
import ru.com.pleasure.service.ChatService

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class ChatControllerTest(
    @Autowired private val chatService: ChatService
) : AbstractContainerTest() {

    @Test
    fun `test function run create and get naming chats`() = runTest {
        chatService.createChat("chats")
        val result = chatService.getChats()
        assert(result[0].name == "chats")
        val chatId = result[0].id
        val resultChatId = chatId?.let { chatService.getChatDetails(it) }
        assert(resultChatId?.name == "chats")

    }
}