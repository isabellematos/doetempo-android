package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Campanha(
    var id: String? = "",
    var title: String? = "",
    var description: String? = "",
    var begin_date: String? = "",
    var end_date: String? = "",
    var home_office: Boolean? = false,
    var how_to_contribute: String? = "",
    var prerequisites: String? = "",
    var id_ngo: String? = "",
   //@SerializedName("campaign_photos") var photos: String = "",
    var photo_url: String? = "",
    var address: Address? = Address(
        number = "",
        complement = "",
        postalCode = ""
    ),
    @SerializedName("campaign_causes") var causes: List<Cause>?,
    var cause: String? = "",
    var ngo: Ong? = Ong(
        id = "",
        name = "",
        email = "",
        password = "",
        cnpj = "",
        foundationDate = "",
        address = Address(postalCode = "", number = "", complement = ""),
        id_type = "",
        description = "",
        type = Type()
    ),
    var campaign_address: Address? = Address(
        id = "",
        number = "",
        complement = "",
        postalCode = ""
    ),
)