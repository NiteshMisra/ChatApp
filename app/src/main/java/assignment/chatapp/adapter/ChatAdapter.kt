package assignment.chatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import assignment.chatapp.R
import assignment.chatapp.extras.AppUtils
import assignment.chatapp.response.Chats

class ChatAdapter(
    private var list: List<Chats>,
    private var userId: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val myChatType: Int = 0
    private val friendsChatType: Int = 1

    class MyChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.message)
        val messageNotSent : ImageView = view.findViewById(R.id.messageNotSent)
        val messageSent : LinearLayout = view.findViewById(R.id.messageSentLayout)
        val time : TextView = view.findViewById(R.id.time)
    }

    class FriendsChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.message)
        val time : TextView = view.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == myChatType) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.element_my_chat, parent, false)
            MyChatViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.element_friend_chat, parent, false)
            return FriendsChatViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == myChatType) {

            val current: Chats = list[position]
            val myChatViewHolder = holder as MyChatViewHolder
            myChatViewHolder.message.text = current.message

            if (current.id == -1){
                myChatViewHolder.messageNotSent.visibility = View.VISIBLE
                myChatViewHolder.messageSent.visibility = View.GONE
            }else{
                myChatViewHolder.messageNotSent.visibility = View.GONE
                myChatViewHolder.messageSent.visibility = View.VISIBLE
                myChatViewHolder.time.text = AppUtils.convertToTime(current.created_at)
            }

        } else {

            val current: Chats = list[position]
            val friendsChatViewHolder = holder as FriendsChatViewHolder
            friendsChatViewHolder.message.text = current.message
            friendsChatViewHolder.time.text = AppUtils.convertToTime(current.created_at)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].user_id == userId) {
            return myChatType
        }else{
            friendsChatType
        }
    }

}