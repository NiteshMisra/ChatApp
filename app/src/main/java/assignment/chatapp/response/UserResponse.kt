package assignment.chatapp.response

data class UserResponse(
    val success : Int,
    val message : String,
    val users : List<Users>
)

data class Users(
    val id : Int,
    val name : String,
    val age : Int,
    val gender : String,
    val image : String,
    val created_at : String,
    val updated_at : String
)