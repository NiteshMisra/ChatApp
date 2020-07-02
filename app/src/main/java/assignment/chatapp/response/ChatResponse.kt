package assignment.chatapp.response

data class ChatResponse(
    val success : Int,
    val message : String,
    val user : Users,
    val chats : List<Chats>
)

data class Chats(
    val id : Int,
    val user_id : Int,
    val message : String,
    val deleted_at : String?,
    val created_at : String,
    val updated_at : String
)