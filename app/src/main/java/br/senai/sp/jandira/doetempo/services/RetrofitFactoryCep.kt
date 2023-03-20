package br.senai.sp.jandira.doetempo.services

import br.senai.sp.jandira.doetempo.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactoryCep {

    val URL = "https://viacep.com.br/ws/"

    val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun retrofitService(): CepRetrofitService{
        return retrofitFactory.create(CepRetrofitService::class.java)
    }
}
