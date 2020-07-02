package assignment.chatapp.extras

import assignment.chatapp.response.Users

interface UserListener {

    fun success(users : List<Users>)

    fun failed(message : String)

    fun progress()

}