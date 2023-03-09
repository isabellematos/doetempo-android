package br.senai.sp.jandira.doetempo.services

import br.senai.sp.jandira.doetempo.model.User
import br.senai.sp.jandira.doetempo.model.UserList
import retrofit2.Call
import retrofit2.http.*

interface UserCall {

    //

    @GET("users")
    fun getAll(): Call<UserList>

    @POST("user")
    fun save(@Body contact: User): Call<User>

    @DELETE("user/{id}")
    fun delete(@Path("id") id: Long): Call<String>
}