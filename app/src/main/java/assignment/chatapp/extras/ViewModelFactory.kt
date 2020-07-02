package assignment.chatapp.extras

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import assignment.chatapp.viewmodel.UserViewModel

class ViewModelFactory(
    var context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(context) as T
    }


}