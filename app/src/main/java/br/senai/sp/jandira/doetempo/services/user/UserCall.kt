package br.senai.sp.jandira.doetempo.services.user

import br.senai.sp.jandira.doetempo.model.*
import retrofit2.Call
import retrofit2.http.*
import retrofit2.*

interface UserCall {

    @GET("test/users")
    fun getAll(): Call<UserList>

    @POST("/user")
    fun save(@Body contact: CreateUser): Call<CreatedUser>

    @GET("/user/{id}")
    fun getById(@Header("Authorization") auth: String, @Path("id")id: String): Call<UserDetailsProfile>

    @PUT("/user/{id}")
    fun update(@Header("Authorization") auth: String, @Path("id")id: String, @Body contact: UpdateUser): Call<PayloadUserUpdate>

    @POST("user/campaign/")
    fun registerUserInCampaign(@Header("Authorization") token: String, @Query("idCampaign") idCampaign: String): Call<RegisterUserInCampaignResponse>

}