package br.senai.sp.jandira.doetempo.services.gender

import br.senai.sp.jandira.doetempo.model.AllGenders
import br.senai.sp.jandira.doetempo.model.Gender
import br.senai.sp.jandira.doetempo.model.GenderList
import br.senai.sp.jandira.doetempo.model.User
import retrofit2.Call
import retrofit2.http.GET

interface GenderCall {

    @GET("gender")
    fun getGenders(): Call<AllGenders>

    @GET("genders")
    fun save(contact: User): Call<Gender>
}