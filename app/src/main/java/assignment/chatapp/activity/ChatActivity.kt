package assignment.chatapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.chatapp.R
import assignment.chatapp.adapter.ChatAdapter
import assignment.chatapp.databinding.ActivityChatBinding
import assignment.chatapp.extras.*
import assignment.chatapp.response.Chat
import assignment.chatapp.response.ChatResponse
import assignment.chatapp.response.Chats
import assignment.chatapp.response.Users
import assignment.chatapp.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import nitish.weather.rest.Coroutines

class ChatActivity : AppCompatActivity(),ChatListener{

    private lateinit var binding : ActivityChatBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var user : Users
    private lateinit var chatsList : ArrayList<Chats>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat)

        initLayout()

        userViewModel.getChats(user.id,this)

        binding.send.setOnClickListener {
            if (binding.message.text.toString().isNotEmpty()){
                val sendMessageBody = SendMessageBody(user.id,binding.message.text.toString())
                userViewModel.sendMessage(sendMessageBody,this)
            }
        }

    }

    private fun initLayout() {

        val value = intent?.getStringExtra(Constants.user)!!
        user = Gson().fromJson(value,Users::class.java)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.userName.text = user.name
        Glide.with(this).load(user.image)
            .error(R.drawable.ic_person)
            .placeholder(R.drawable.ic_person)
            .into(binding.image)

        chatsList = ArrayList()
        chatAdapter = ChatAdapter(chatsList,user.id)

        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        manager.reverseLayout = false
        binding.chatRcv.layoutManager = manager

        val viewModelFactory = ViewModelFactory(this)
        userViewModel = ViewModelProvider(this,viewModelFactory).get(UserViewModel::class.java)
    }

    override fun success(chatResponse: ChatResponse) {
        Coroutines.main {
            binding.progressBar.visibility = View.GONE

            Glide.with(this).load(chatResponse.user.image)
                .error(R.drawable.ic_person)
                .placeholder(R.drawable.ic_person)
                .into(binding.image)

            chatsList.addAll(chatResponse.chats)
            chatsList.sortBy { it.created_at }
            chatAdapter = ChatAdapter(chatsList,user.id)
            binding.chatRcv.adapter = chatAdapter
            chatAdapter.notifyDataSetChanged()
        }
    }

    override fun failed(message: String) {
        Coroutines.main {
            binding.progressBar.visibility = View.GONE
            AppUtils.toast(message,this)
        }
    }

    override fun progress() {
        Coroutines.main {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    override fun msgSending(message: String) {
        Coroutines.main {
            val chats = Chats(-1,user.id,message,null,"","")
            chatsList.add(chats)
            chatAdapter.notifyItemInserted(chatsList.size - 1)
            chatAdapter.notifyItemRangeChanged(chatsList.size-1,chatsList.size)
            binding.chatRcv.scrollToPosition(chatsList.size - 1)
        }
    }

    override fun msgSent(chat : Chat) {
        Coroutines.main {
            val chats = Chats(chat.id,chat.user_id,chat.message,null,chat.created_at,chat.updated_at)
            chatsList[chatsList.size - 1] = chats
            chatAdapter.notifyItemChanged(chatsList.size - 1)
        }
    }

    override fun msgSendingFailed(message: String) {
        Coroutines.main { AppUtils.toast(message,this) }
    }
}