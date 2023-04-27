package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Photo (
    @SerializedName("photo_url")
    var photoURL: String = "",
    var id: String = ""
)