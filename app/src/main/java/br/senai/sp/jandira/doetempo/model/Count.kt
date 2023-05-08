package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Count(
    val comment: Long? = null,

    @SerializedName("post_ngo")
    val postNgo: Long? = null,

    @SerializedName("post_photo")
    val postPhoto: Long? = null,

    @SerializedName("post_user")
    val postUser: Long? = null,

    @SerializedName("post_likes")
    val postLikes: Long? = null
)
