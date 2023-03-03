package br.senai.sp.jandira.doetempo.model

data class CadastroUser(
    var name: String,
    var email: String,
    var password: String,
    var cpf: String,
    var birthdate: String,
    var postal_code: String,
    var number: String,
    var gender: String,
)
