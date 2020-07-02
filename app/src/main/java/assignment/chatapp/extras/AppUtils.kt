@file:Suppress("DEPRECATION")

package assignment.chatapp.extras

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class AppUtils {

    companion object {

        fun isNetworkAvailable(context: Context) : Boolean{
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        fun toast(message : String,context: Context){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

        fun convertToTime(createdAt : String) : String{
            val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
            val todayDate = Calendar.getInstance().time
            val currentDate = format.format(todayDate)
            val date1 = format.parse(currentDate)!!
            val date2 = format.parse(createdAt)!!

            var difference = date2.time - date1.time

            val secInMilli = 1000
            val minInMilli = secInMilli * 60
            val hourInMilli = minInMilli * 60
            val daysInMilli = hourInMilli * 24

            val days = difference / daysInMilli
            difference %= daysInMilli

            val hours = difference / hourInMilli
            difference %= hourInMilli

            val minutes = difference / minInMilli
            difference %= minInMilli

            val seconds = difference / secInMilli

            return if (days > 0){
                createdAt.substring(0,9)
            }else{
                if (hours > 0){
                    ("${abs(hours)} hours")
                }else{
                    if (minutes > 0){
                        ("${abs(minutes)} min")
                    }else{
                        ("${abs(seconds)} sec")
                    }
                }
            }
        }

    }
}