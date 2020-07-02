package assignment.chatapp.rest

import assignment.chatapp.extras.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val retrofit: Retrofit

    companion object {

        private var instance : RetrofitClient?= null

        @Synchronized
        fun getInstance(): RetrofitClient {
            if (instance == null) {
                instance = RetrofitClient()
            }
            return instance!!
        }
    }

    val api: Api
        get() {
            return retrofit.create(Api::class.java)
        }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        val gson = GsonBuilder().setLenient().create()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}