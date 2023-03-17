package br.senai.sp.jandira.doetempo.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import br.senai.sp.jandira.doetempo.constants.Constants

class RetrofitFactory {

    companion object {
        private lateinit var instance: Retrofit

        // :: é pra pegar a instancia do objeto
        fun getRetrofit(): Retrofit {
            if (!::instance.isInitialized) {
                instance = Retrofit
                    .Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return instance
        }
    }
}