package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class Cep(
    var cep: String? = null,
    var logradouro: String? = null,
    var complemento: String? = null,
    var bairro: String? = null,
    // trocando nome vindo do json
    @SerializedName("localidade") var cidade: String? = null,
    @SerializedName("uf") var estado: String? = null,
)
