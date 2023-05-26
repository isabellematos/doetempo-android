package br.senai.sp.jandira.doetempo.services.cep

import br.senai.sp.jandira.doetempo.model.Cep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepRetrofitService {

    @GET("{cep}/json")
    fun getAddress(@Path("cep") cep: String): Call<Cep>
}