package br.senai.sp.jandira.doetempo.model

data class GenderList(
    var id: String? = "",
    var name: String? = "",
    var abbreviation: String? = ""
){
    override fun toString(): String {
        return "GenderList(id='$id', name='$name')"
    }
}
