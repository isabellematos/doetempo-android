package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Post(
    var id: String? = "",
    var content: String? = "",
    var created_at: String? = "",
//    @SerializedName("PostNgo") var tbl_ngo: List<Ong>? = listOf(),
    @SerializedName("post_ngo") var ngo: List<Ong?>? = null,
//        id = "",
//        name = "",
//        email = "",
//        id_type = "",
//        photo_url = "",
//        foundationDate = "",
//        address = Address(postalCode = "", number = "", complement = ""),
//        type = Type(name = ""),
//    ),
   //@SerializedName("post_user") var user: List<User?>? = null,
//        id = "",
//        name = "",
//        email = "",
//        id_type = "",
//        photo_url = "",
//        birthdate = "",
//        address = Address(postalCode = "", number = "", complement = ""),
//        type = Type(name = "")
//    ),
    //@SerializedName("post_photo") var post_photo: List<Photo> = listOf(),
    //@SerializedName("_count") var count: Count?
)
