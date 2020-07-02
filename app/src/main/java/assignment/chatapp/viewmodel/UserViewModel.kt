package assignment.chatapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import assignment.chatapp.extras.AppUtils
import assignment.chatapp.extras.ChatListener
import assignment.chatapp.extras.SendMessageBody
import assignment.chatapp.extras.UserListener
import assignment.chatapp.rest.RetrofitClient
import nitish.weather.rest.Coroutines

class UserViewModel(
    private var context: Context
) : ViewModel() {

    fun getUsers(userListener: UserListener){
        userListener.progress()

        if (AppUtils.isNetworkAvailable(context)){
            Coroutines.io {
                try{
                    val response = RetrofitClient.getInstance().api.getUsers()
                    if (response.isSuccessful){
                        val body = response.body()!!
                        if (body.success > 0){
                            userListener.success(body.users)
                        }else{
                            userListener.failed(body.message)
                        }
                    }else{
                        userListener.failed(response.errorBody().toString())
                    }

                }catch (e : Exception){
                    userListener.failed(e.message.toString())
                }

            }
        }else{
            userListener.failed("No Internet Connection")
        }
    }

    fun getChats(userId : Int,chatListener: ChatListener){

        if (AppUtils.isNetworkAvailable(context)){
            Coroutines.io {
                try{
                    val response = RetrofitClient.getInstance().api.getUserChat(userId)
                    if (response.isSuccessful){
                        val body = response.body()!!
                        if (body.success > 0){
                            chatListener.success(body)
                        }else{
                            chatListener.failed(body.message)
                        }
                    }else{
                        chatListener.failed(response.errorBody().toString())
                    }

                }catch (e : Exception){
                    chatListener.failed(e.message.toString())
                }

            }
        }else{
            chatListener.failed("No Internet Connection")
        }

    }

    fun sendMessage(sendMessageBody: SendMessageBody,chatListener: ChatListener){
        chatListener.msgSending(sendMessageBody.message)
        if (AppUtils.isNetworkAvailable(context)){
            Coroutines.io {
                try{
                    val response = RetrofitClient.getInstance().api.sendMessage(sendMessageBody)
                    if (response.isSuccessful){
                        val body = response.body()!!
                        if (body.success > 0){
                            chatListener.msgSent(body.chat)
                        }else{
                            chatListener.msgSendingFailed(body.message)
                        }
                    }else{
                        chatListener.msgSendingFailed(response.errorBody().toString())
                    }

                }catch (e : Exception){
                    chatListener.msgSendingFailed(e.message.toString())
                }

            }
        }else{
            chatListener.failed("No Internet Connection")
        }
    }

}