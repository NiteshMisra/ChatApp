package assignment.chatapp.extras

import assignment.chatapp.response.Chat
import assignment.chatapp.response.ChatResponse

interface ChatListener {

    fun success(chatResponse: ChatResponse)

    fun failed(message : String)

    fun progress()

    fun msgSending(message: String)

    fun msgSent(chat : Chat)

    fun msgSendingFailed(message : String)

}