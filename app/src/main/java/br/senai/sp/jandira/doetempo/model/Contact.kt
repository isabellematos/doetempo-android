package br.senai.sp.jandira.doetempo.model

data class Contact(
    var id: Long = 0,
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var password: String = "",
    var cpf: String = "",
    var birthdate: String = "",
    var postal_code: String = "",
    var number: String = "",
    var gender: String = "",
){
    override fun toString(): String {
        return "Contact(id=$id, name='$name', email='$email', phone='$phone', password='$password', cpf='$cpf', birthdate='$birthdate', postal_code='$postal_code', number='$number', gender='$gender')"
    }
}
