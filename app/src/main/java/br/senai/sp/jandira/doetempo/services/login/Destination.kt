package br.senai.sp.jandira.doetempo.services.login

sealed class Destination(val route: String) {
    object Login : Destination(route = "login")
    object CampanhaDetailsActivity : Destination(route = "home")
    companion object {
        fun getStartDestination() = Login.route
    }
}
