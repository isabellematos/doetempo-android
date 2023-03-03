package br.senai.sp.jandira.doetempo.services

import br.senai.sp.jandira.doetempo.model.CadastroUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CadastroUserRetrofitService {


    @GET("{cadastro}/json/")
    fun getCadastro(
        @Path("name")name: String,
        @Path("email")email: String,
        @Path("password")password: String,
        @Path("cpf")cpf: String,
        @Path("birthdate")birthdate: String,
        @Path("postal_code")postal_code: String,
        @Path("number")number: String,
        @Path("gender")gender: String,


    ): Call<List<CadastroUser>>
}