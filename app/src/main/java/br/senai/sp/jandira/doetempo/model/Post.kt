package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Post(
    var id: String? = "",
    var content: String? = "",
    var created_at: String = "",
    @SerializedName("PostNgo") var tbl_ngo: Ong? = Ong(
        id = "",
        name = "",
        email = "",
        id_type = "",
        photoURL = "",
        foundationDate = "",
        address = Address(postalCode = "", number = "", complement = ""),
    ),
    @SerializedName("PostUser") var tbl_user: User? = User(
        id = "",
        name = "",
        email = "",
        id_type = "",
        photoURL = "",
        birthdate = "",
        address = Address(postalCode = "", number = "", complement = ""),
    ),
    @SerializedName("PostPhoto") var post_photo: List<Photo>? = listOf(),
    var photoURL: String? = "",
)
