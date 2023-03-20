package br.senai.sp.jandira.doetempo.services.user

import br.senai.sp.jandira.doetempo.model.*
import retrofit2.Call
import retrofit2.http.*
import retrofit2.*

interface UserCall {

    @GET("test/users")
    fun getAll(): Call<UserList>

    @POST("user")
    fun save(@Body contact: User): Call<CreatedUser>

    @DELETE("user/{id}")
    fun delete(@Path("id") id: Long): Call<String>
}