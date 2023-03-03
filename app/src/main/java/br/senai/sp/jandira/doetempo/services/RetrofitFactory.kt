package br.senai.sp.jandira.doetempo.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    val URL = ""

    val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun retrofitService(): CadastroUserRetrofitService{
        return retrofitFactory.create(CadastroUserRetrofitService::class.java)
    }
}