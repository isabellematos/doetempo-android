package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class CreatePost(
    var content: String? = null,
    @SerializedName("type_of_user") var typeUser: String? = null,
    var photos: List<String>? = null
)