package br.senai.sp.jandira.doetempo.model

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class Comment(
    val id: String = "",
    val content: String = "",

    @SerializedName("created_at")
    val createdAt: String = "",

    @SerializedName("comment_user")
    val commentUser: List<UserDetails>,

    @SerializedName("comment_ngo")
    val commentNgo: List<OngDetails>,

    @SerializedName("_count")
    val count: Count
)
