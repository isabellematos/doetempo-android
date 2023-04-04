package br.senai.sp.jandira.doetempo.services.ong

import br.senai.sp.jandira.doetempo.model.CreatedOng
import br.senai.sp.jandira.doetempo.model.Ong
import br.senai.sp.jandira.doetempo.model.OngDetalhes
import br.senai.sp.jandira.doetempo.model.OngList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OngCall {

    @GET("/ngo/")
    fun getAll(): Call<OngList>

    @GET("/ngo/{id}")
    fun getById(@Path("id")id: String): Call<Ong>

    @POST("/ngo/")
    fun save(@Body contact: Ong): Call<CreatedOng>
}