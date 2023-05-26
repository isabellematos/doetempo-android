package br.senai.sp.jandira.doetempo.services

import android.net.Uri
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import br.senai.sp.jandira.doetempo.constants.Constants
import br.senai.sp.jandira.doetempo.services.user.UserCall
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class RetrofitFactory {

    companion object {
//        val gson: Gson = GsonBuilder().registerTypeAdapter(Uri::class.java, UriTypeAdapter()).create()

        private lateinit var instance: Retrofit

        val okhttp = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        // :: é pra pegar a instancia do objeto
        fun getRetrofit(): Retrofit {
            if (!::instance.isInitialized) {
                instance = Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create()) // create(gson)
                    .baseUrl(Constants.BASE_URL)
                    .client(okhttp)
                    .build()
            }

            return instance
        }
        fun retrofitUserServices(): UserCall {
            instance = getRetrofit()
            return instance.create(UserCall::class.java)
        }

    }
}