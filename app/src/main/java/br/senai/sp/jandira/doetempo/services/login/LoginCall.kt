package br.senai.sp.jandira.doetempo.services.login


import br.senai.sp.jandira.doetempo.model.LoginDto
import br.senai.sp.jandira.doetempo.model.TokenDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//@POST("/ngo/")
//fun save(@Body contact: Login): Call<Login>
//}

interface AuthApiService {

    @POST("/auth/")
    fun getLogin(@Body loginDto: LoginDto) :
            Call<TokenDto>
}