package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

data class Ong(
    var id: String = "",
    var name: String = "",
    var email: String? = "",
    var password: String? = "",
    var cnpj: String? = "",
    @SerializedName("foundation_date") var foundationDate: String?,
    var address: Address?,
    var id_type: String? = "",
    var description: String?  = "",
    var photo_url: String? = "",
    var type: Type?
){
    override fun toString(): String {
        return "Ong(id='$id', name='$name', email='$email', password='$password', cnpj='$cnpj', foundation_date='$foundationDate')"
    }
}
