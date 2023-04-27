package br.senai.sp.jandira.doetempo.services.post

import br.senai.sp.jandira.doetempo.model.*
import retrofit2.Call
import retrofit2.http.*

interface PostCall {

    @GET("/post/")
    fun getAll(): Call<PostList>

    @GET("/post/{id}")
    fun getById(@Path("id")id: String): Call<CampanhaDetalhes>


    @POST("/post/")
    fun save(@Header("Authorization") token: String, @Body contact: Campanha): Call<CreatedCampanha>

}