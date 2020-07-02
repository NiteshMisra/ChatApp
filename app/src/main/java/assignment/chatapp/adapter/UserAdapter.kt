package assignment.chatapp.adapter

import android.content.Context
import android.content.Intent
import assignment.chatapp.R
import assignment.chatapp.activity.ChatActivity
import assignment.chatapp.databinding.ElementUserBinding
import assignment.chatapp.extras.Constants
import assignment.chatapp.response.Users
import com.bumptech.glide.Glide
import com.google.gson.Gson

class UserAdapter(private var context: Context) : BaseRecyclerViewAdapter<Users, ElementUserBinding>() {

    override fun getLayout() = R.layout.element_user

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ElementUserBinding>,
        position: Int
    ) {

        val currentUser = items[position]
        holder.binding.userName.text = currentUser.name

        Glide.with(context).load(currentUser.image)
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(holder.binding.image)

        holder.binding.layout.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(Constants.user, Gson().toJson(currentUser))
            context.startActivity(intent)
        }

    }

}