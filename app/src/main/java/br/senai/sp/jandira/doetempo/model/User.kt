package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class User(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var cpf: String? = null,
    var birthdate: String? = null,
    var gender: GenderDTO? = null,
    var description: String? = null,
    var photo_url: String? = null,
    @SerializedName("user_address")
    val address: Address? = null,
    var type: Type? = null,
    @SerializedName("user_phone")
    var userPhone: UserPhone? = null,
    @SerializedName("post_user")
    var postUser: List<PostUser>? = null,
    @SerializedName("attached_link")
    val attachedLink: List<AttachedLink>? = null,
    @SerializedName("_count")
    val count: Count? = null
)
