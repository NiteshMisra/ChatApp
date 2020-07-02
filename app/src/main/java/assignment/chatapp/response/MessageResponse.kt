package assignment.chatapp.response

data class MessageResponse(
    val success : Int,
    val message : String,
    val user : Users,
    val chat : Chat
)

data class Chat(
    val message : String,
    val user_id : Int,
    val updated_at : String,
    val created_at : String,
    val id : Int
)