package br.senai.sp.jandira.doetempo.services

import br.senai.sp.jandira.doetempo.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactoryCep {

    companion object {
        private lateinit var instanceCep: Retrofit
        fun getRetrofitCep(): Retrofit {
            if (!::instanceCep.isInitialized) {
                instanceCep = Retrofit
                    .Builder()
                    .baseUrl(Constants.CEP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return instanceCep
        }
    }

}