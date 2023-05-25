package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Ong(
    var id: String = "",
    var name: String = "",
    var email: String? = "",
    var password: String? = "",
    var cnpj: String? = "",
    @SerializedName("foundation_date") var foundationDate: String?,
    var address: Address?,
    var id_type: String? = "",
    var description: String?  = "",
    var photo_url: String? = "",
    var type: Type?,

    @SerializedName("attached_link")
    val attachedLink: List<AttachedLink>? = null,

    @SerializedName("post_ngo")
    val postNgo: List<PostNgo>? = null,

    @SerializedName("ngo_address")
    val ngoAddress: NgoAddress? = null,

//    @SerializedName("_count")
//    val count: Count? = null

    ){
    override fun toString(): String {
        return "Ong(id='$id', name='$name', email='$email', password='$password', cnpj='$cnpj', foundation_date='$foundationDate')"
    }
}
