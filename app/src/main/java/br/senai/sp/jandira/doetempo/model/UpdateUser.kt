package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class UpdateUser(
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var cpf: String? = null,
    var birthdate: String? = null,
    var id_gender: String? = null,
    var description: String? = null,
    var photo_url: String? = null,
    @SerializedName("user_address")
    val address: Address? = null,
    var type: Type? = null,
    @SerializedName("user_phone")
    var userPhone: UserPhone? = null,
    @SerializedName("attached_link")
    val attachedLink: List<AttachedLink?>? = null,

)
