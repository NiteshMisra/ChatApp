package assignment.chatapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.chatapp.R
import assignment.chatapp.adapter.UserAdapter
import assignment.chatapp.databinding.ActivityUsersBinding
import assignment.chatapp.extras.UserListener
import assignment.chatapp.extras.ViewModelFactory
import assignment.chatapp.response.Users
import assignment.chatapp.viewmodel.UserViewModel
import nitish.weather.rest.Coroutines

class Users : AppCompatActivity(), UserListener {

    private lateinit var binding : ActivityUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_users)

        binding.userRcv.layoutManager = LinearLayoutManager(this)
        binding.userRcv.setHasFixedSize(true)

        val viewModelFactory = ViewModelFactory(this)
        val userViewModel = ViewModelProvider(this,viewModelFactory).get(UserViewModel::class.java)
        userViewModel.getUsers(this)

        binding.retry.setOnClickListener {
            userViewModel.getUsers(this)
        }
    }

    override fun success(users: List<Users>) {
        Coroutines.main {
            val userAdapter = UserAdapter(this)
            userAdapter.addItems(users)
            binding.userRcv.adapter = userAdapter
            userAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
            binding.errorLayout.visibility = View.GONE
            binding.userRcv.visibility = View.VISIBLE
        }
    }

    override fun failed(message: String) {
        Coroutines.main {
            binding.errorBody.text = message
            binding.progressBar.visibility = View.GONE
            binding.errorLayout.visibility = View.VISIBLE
            binding.userRcv.visibility = View.GONE
        }
    }

    override fun progress() {
        Coroutines.main {
            binding.progressBar.visibility = View.VISIBLE
            binding.errorLayout.visibility = View.GONE
            binding.userRcv.visibility = View.GONE
        }
    }
}