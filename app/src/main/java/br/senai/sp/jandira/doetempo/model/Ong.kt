package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Ong(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var cnpj: String = "",
    var foundation_date: String,
    val address: Address,
    val id_type: String?
){
    override fun toString(): String {
        return "Ong(id='$id', name='$name', email='$email', password='$password', cnpj='$cnpj', foundation_date='$foundation_date')"
    }
}
