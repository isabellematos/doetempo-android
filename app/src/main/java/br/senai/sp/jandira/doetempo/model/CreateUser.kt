package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class CreateUser(

    var id: String? = null,
    var name: String? = null,
    var email: String = "",
    var password: String? = "",
    var cpf: String = "",
    var birthdate: String,
    var description: String?  = "",
    var address: Address?,
    var gender: String = "",
    var photo_url: String? = "",
    var type: Type?,
//    @SerializedName("user_phone")
//    var userPhone: UserPhone? = null,
//    @SerializedName("attached_link")
//    val attachedLink: List<AttachedLink>? = null,
//    @SerializedName("_count")
//    val count: Count? = null
)


