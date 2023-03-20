package br.senai.sp.jandira.doetempo.services

import br.senai.sp.jandira.doetempo.model.Cep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepRetrofitService {

    //https://viacep.com.br/ws/06626420/json/
    @GET("{cep}/json/")
    fun getCep(@Path("cep") cep: String): Call<Cep> // ele sabe q cep eh o valor a ser substituido, Call = chamar arquivo na model, cep ou qualquer outro

}