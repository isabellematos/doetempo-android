package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class UserAddress(

    val id: String? = null,

    @SerializedName("id_address")
    val idAddress: String? = null,

    @SerializedName("id_user")
    val idUser: String? = null,

    val address: Address? = null


)
