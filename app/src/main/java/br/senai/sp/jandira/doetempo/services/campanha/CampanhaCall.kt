package br.senai.sp.jandira.doetempo.services.campanha

import br.senai.sp.jandira.doetempo.model.Campanha
import br.senai.sp.jandira.doetempo.model.CampanhaList
import br.senai.sp.jandira.doetempo.model.OngList
import retrofit2.Call
import retrofit2.http.GET

interface CampanhaCall {

    @GET("/campaign/")
    fun getAll(): Call<CampanhaList>
}