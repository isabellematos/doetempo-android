package br.senai.sp.jandira.doetempo.services.campanha

import br.senai.sp.jandira.doetempo.CampanhaDetailsActivity
import br.senai.sp.jandira.doetempo.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface CampanhaCall {

    @GET("/campaign/")
    fun getAll(): Call<CampanhaList>

    @GET("/campaign/{id}")
    fun getById(@Path("id")id: String): Call<Campanha>


    @POST("/campaign/")
    fun save(@Header("Authorization") token: String, @Body contact: Campanha): Call<CreatedCampanha>

}