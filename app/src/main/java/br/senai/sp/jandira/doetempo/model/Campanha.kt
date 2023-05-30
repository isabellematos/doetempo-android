package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Campanha(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var begin_date: String? = null,
    var end_date: String? = null,
    var home_office: Boolean? = false,
    var how_to_contribute: String? = null,
    var prerequisites: String? = null,
    var id_ngo: String? = null,
    @SerializedName("campaign_photos") var campaignPhotos: List<Photo>? = null,
    var photo_url: String? = null,
    @SerializedName("campaign_address") var campaignAddress: Address? = null,
    //@SerializedName("campaign_causes") var causes: List<Cause>?,
    //var cause: String? = "",
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
    var campaign_address: Address? = null,
)