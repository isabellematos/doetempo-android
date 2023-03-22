package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Cep(
    var cep: String,
    var logradouro: String,
    var complemento: String? = "",
    var bairro: String,
    // trocando nome vindo do json
    @SerializedName("localidade") var cidade: String,
    @SerializedName("uf") var estado: String,
)
