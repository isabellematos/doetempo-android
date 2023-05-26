package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class CreatePost(
    var content: String,
    @SerializedName("type_of_user") var typeUser: String,
    var photos: List<HashMap<String, Any>>
)