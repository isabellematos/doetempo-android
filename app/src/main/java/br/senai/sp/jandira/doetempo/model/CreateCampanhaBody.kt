package br.senai.sp.jandira.doetempo.model

data class CreateCampanhaBody(
    var id: String? = "",
    var title: String? = "",
    var description: String? = "",
    var begin_date: String? = "",
    var end_date: String? = "",
    var home_office: Boolean? = false,
    var how_to_contribute: String? = "",
    var prerequisites: String? = "",
    var id_ngo: String? = "",
//    var ong: Ong? = Ong(
//        id = "",
//        name = "",
//        foundationDate = "",
//        type = Type(),
//        address = Address(
//        number = "",
//        complement = "",
//        postalCode = ""
//        ),
//    ),
//@SerializedName("campaign_photos") var photos: String = "",
    var photo_url: List<String>? = listOf(""),
    var address: Address? = Address(
    number = "",
    complement = "",
    postalCode = ""
),
    var campaign_causes: List<Cause>
)