package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class AttachedLink(

    val id: String? = null,

    @SerializedName("attached_link")
    val attachedLink: String? = null,

    @SerializedName("id_source")
    val idSource: String? = null,

    @SerializedName("id_user")
    val idUser: String? = null,

    @SerializedName("id_ngo")
    val idNgo: String? = null,

    val source: Source? = null

)
