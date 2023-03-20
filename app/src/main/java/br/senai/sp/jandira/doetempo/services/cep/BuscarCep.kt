package br.senai.sp.jandira.doetempo.services.cep

import android.util.Log
import br.senai.sp.jandira.doetempo.model.Cep
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun buscarCep(cep: String, onComplete: (String) -> Unit){

    var logradouro = ""

    val call = RetrofitFactoryCep()
        .retrofitService()
        .getCep(cep)

    call.enqueue(object: Callback<Cep> {
        override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
            Log.i("ds3m", response.body()!!.toString())
            logradouro =  response.body()!!.logradouro ?: "Cep não encontrado"
            onComplete.invoke(logradouro)
        }//quanto terminar de ser executado o oncomplete invoca o logradouro

        override fun onFailure(call: Call<Cep>, t: Throwable) {
            Log.i("ds3m", t.message.toString())
        }

    })
}