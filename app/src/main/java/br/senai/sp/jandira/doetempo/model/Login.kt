package br.senai.sp.jandira.doetempo.model

import retrofit2.http.POST

data class Login(
    var email: String = "",
    var password: String = ""
){
    override fun toString(): String {
        return "LoginCall(email='$email', password='$password')"
    }
}
