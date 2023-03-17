package br.senai.sp.jandira.doetempo.services

import br.senai.sp.jandira.doetempo.model.Cep
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun buscarCep(cep: String, onComplete: (String) -> Unit){

    var logradouro = ""

    val call = RetrofitFactory()
        .retrofitService()
        .getCep(cep)

    call.enqueue(object: Callback<Cep> {
        override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
            logradouro =  response.body()!!.logradouro ?: "Cep não encontrado"
            onComplete.invoke(logradouro)
        }//quanto terminar de ser executado o oncomplete invoca o logradouro

        override fun onFailure(call: Call<Cep>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}