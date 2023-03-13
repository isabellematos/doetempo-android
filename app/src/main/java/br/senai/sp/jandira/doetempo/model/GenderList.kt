package br.senai.sp.jandira.doetempo.model

data class GenderList(
    var id: String = "",
    var name: String = "",
){
    override fun toString(): String {
        return "GenderList(id='$id', name='$name')"
    }
}
