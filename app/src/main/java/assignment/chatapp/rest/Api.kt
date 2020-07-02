package assignment.chatapp.rest

import assignment.chatapp.extras.SendMessageBody
import assignment.chatapp.response.ChatResponse
import assignment.chatapp.response.MessageResponse
import assignment.chatapp.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("users/get")
    suspend fun getUsers() : Response<UserResponse>

    @GET("users/{userId}/chats")
    suspend fun getUserChat(
        @Path("userId") userId : Int
    ) : Response<ChatResponse>

    @POST("users/chat")
    suspend fun sendMessage(
        @Body sendMessageBody: SendMessageBody
    ) : Response<MessageResponse>

}