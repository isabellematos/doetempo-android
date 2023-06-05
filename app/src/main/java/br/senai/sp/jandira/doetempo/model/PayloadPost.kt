package br.senai.sp.jandira.doetempo.model

data class PayloadPost(
    var message: String,
    var payload: Post? = null
)
