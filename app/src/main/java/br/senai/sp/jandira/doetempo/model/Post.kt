package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Post(
    var id: String? = "",
    var content: String? = "",
    var created_at: String = "",
   // @SerializedName("PostNgo") var tbl_ngo: List<Ong>? = listOf(),
    @SerializedName("post_ngo") var ngo: Ong? = Ong(
        id = "",
        name = "",
        email = "",
        id_type = "",
        photo_url = "",
        foundationDate = "",
        address = Address(postalCode = "", number = "", complement = ""),
    ),
    @SerializedName("post_user") var user: User? = User(
        id = "",
        name = "",
        email = "",
        id_type = "",
        photo_url = "",
        birthdate = "",
        address = Address(postalCode = "", number = "", complement = ""),
    ),
    @SerializedName("post_photo") var post_photo: List<Photo>? = listOf(),
    var photo_url: String? = "",
)
