package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Photo (
    var id: String = "",
    @SerializedName("photo_url") var photoUrl: String? = "",

)