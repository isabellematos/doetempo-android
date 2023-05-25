package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class NgoAddress(

    val id: String? = null,

    @SerializedName("id_address")
    val idAddress: String? = null,

    @SerializedName("id_ngo")
    val idNgo: String? = null,

    val address: Address? = null


)
