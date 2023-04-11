package br.senai.sp.jandira.doetempo.model

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
    var photoURL: String? = "https://firebasestorage.googleapis.com/v0/b/doe-tempo-50ccb.appspot.com/o/images%2Fdefault.png?alt=media&token=337960c3-810d-42bc-b17e-14dd945acc2c",
    var address : Address? = Address(
        number = "",
        complement = "",
        postalCode = ""
    ),
    var causes: List<Cause>? = listOf(Cause(id = "07c4c9d2-e633-48ca-848b-0f3a0b74d405"), Cause(id = "70a2ad4b-c472-4bbc-b7bf-86e0ec1d4dc3")),
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