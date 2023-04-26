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
    @SerializedName("tbl_campaign_photos") var photos: List<Photo>? = listOf(),
    var photoURL: String? = "",
    var address: Address? = Address(
        number = "",
        complement = "",
        postalCode = ""
    ),
    @SerializedName("tbl_campaign_causes") var causes: List<Cause>?,
    var cause: String? = "",
    var tbl_ngo: Ong? = Ong(
        id = "",
        name = "",
        email = "",
        password = "",
        cnpj = "",
        foundationDate = "",
        address = Address(postalCode = "", number = "", complement = ""),
        id_type = "",
        description = ""
    ),
    var tbl_campaign_address: Address? = Address(
        number = "",
        complement = "",
        postalCode = ""
    ),
)