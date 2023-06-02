package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Post(
    var id: String? = "",
    var content: String? = "",
    var created_at: String? = "",
    @SerializedName("post_photo") var post_photo: List<Photo?>? = null,
//    @SerializedName("PostNgo") var tbl_ngo: List<Ong>? = listOf(),
    @SerializedName("post_ngo") var ngo: List<OngDetails?>? = null,
    @SerializedName("post_user") var user: List<UserDetails?>? = null,
    var comment: List<Comment>,
    @SerializedName("_count") var count: Count?
)
