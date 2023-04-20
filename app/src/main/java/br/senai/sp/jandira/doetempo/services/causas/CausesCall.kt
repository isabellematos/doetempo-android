package br.senai.sp.jandira.doetempo.services.causas

import br.senai.sp.jandira.doetempo.model.CampanhaDetalhes
import br.senai.sp.jandira.doetempo.model.Cause
import br.senai.sp.jandira.doetempo.model.CauseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CausesCall {

    @GET("/causes/")
    fun getAll(): Call<CauseList>

    @GET("/causes/")
    fun get(): Call<Cause>

}