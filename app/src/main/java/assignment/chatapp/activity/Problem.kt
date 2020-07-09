package assignment.chatapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import assignment.chatapp.R
import assignment.chatapp.databinding.ActivityProblemBinding

class Problem : AppCompatActivity() {

    private lateinit var binding : ActivityProblemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_problem)


        var checkingNumber = 2
        var lastNumber = 0
        var i = 2

        while (i < 1000000){
            lastNumber = i
            if (i != checkingNumber*8){
                i *= 2
            }else{
                i = (i-1)/3
                checkingNumber = i
            }
        }

        binding.text.text = lastNumber.toString()

    }
}