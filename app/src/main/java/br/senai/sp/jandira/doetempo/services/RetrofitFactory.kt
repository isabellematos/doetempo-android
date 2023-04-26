package br.senai.sp.jandira.doetempo.services

import android.net.Uri
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import br.senai.sp.jandira.doetempo.constants.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class RetrofitFactory {

    companion object {
//        val gson: Gson = GsonBuilder().registerTypeAdapter(Uri::class.java, UriTypeAdapter()).create()

        private lateinit var instance: Retrofit

        // :: é pra pegar a instancia do objeto
        fun getRetrofit(): Retrofit {
            if (!::instance.isInitialized) {
                instance = Retrofit
                    .Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // create(gson)
                    .build()
            }

            return instance
        }
    }
}