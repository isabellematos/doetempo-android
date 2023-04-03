package br.senai.sp.jandira.doetempo.services.campanha

import br.senai.sp.jandira.doetempo.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CampanhaCall {

    @GET("/campaign/")
    fun getAll(): Call<CampanhaList>

    @GET("/campaign/")
    fun get(): Call<Campanha>

    @POST("/campaign/")
    fun save(@Body contact: Campanha): Call<CreatedCampanha>


}