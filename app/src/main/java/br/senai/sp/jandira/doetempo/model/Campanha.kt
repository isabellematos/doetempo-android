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
    var tbl_campaign_address: Address,
    )