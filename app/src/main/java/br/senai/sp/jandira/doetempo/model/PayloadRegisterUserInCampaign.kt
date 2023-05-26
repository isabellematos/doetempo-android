package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class PayloadRegisterUserInCampaign(

    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val cpf: String? = null,
    @SerializedName("id_gender")
    val idGender: String? = null,
    val birthdate: String? = null,
    val rg: String? = null,
    @SerializedName("id_type")
    val idType: String? = null,
    val description: Any? = null,
    @SerializedName("banner_photo")
    val bannerPhoto: String? = null,
    @SerializedName("photo_url")
    val photoURL: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null

)
