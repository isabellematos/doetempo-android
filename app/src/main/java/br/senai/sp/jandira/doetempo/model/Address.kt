package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Address (
    @SerializedName("postal_code") val postalCode: String,
    val number: String,
    val complement: String? = ""
)