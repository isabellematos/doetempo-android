package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Ong(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var cnpj: String? = null,
    @SerializedName("foundation_date") var foundationDate: String? = null,
    var address: Address? = null,
    var id_type: String? = null,
    var description: String?  = null,
    var photo_url: String? = null,
    var type: Type? = null,

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
