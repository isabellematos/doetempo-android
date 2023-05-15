package br.senai.sp.jandira.doetempo.model

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class Comment(
    val id: String? = null,
    val content: String? =  null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("comment_user")
    val commentUser: List<UserDetails>? = null,

    @SerializedName("comment_ngo")
    val commentNgo: List<OngDetails>? = null,

    @SerializedName("_count")
    val count: Count? = null
)
