package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
data class TokenDto(
    @SerializedName("token") val accessTokenVerify: String,
    @SerializedName("data") val dataUser: LoginData?
)
//data class Login(
//    var email: String = "",
//    var password: String = "",
//    var type: String = ""
//){
//    override fun toString(): String {
//        return "LoginCall(email='$email', password='$password')"
//    }
//}

