package br.senai.sp.jandira.doetempo.services.causas

import br.senai.sp.jandira.doetempo.model.*
import retrofit2.Call
import retrofit2.http.*

interface CausesCall {

    @GET("/causes/")
    fun getAll(): Call<CauseList>

    @GET("/causes/")
    fun get(): Call<Cause>

    @POST("/causes/")
    fun save(@Header("Authorization") token: String, @Body contact: Cause): Call<CreatedCause>

}