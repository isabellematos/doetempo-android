package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class CampaignPhoto(
    @SerializedName("photo_url")
    var photoURL: String? = null


)
