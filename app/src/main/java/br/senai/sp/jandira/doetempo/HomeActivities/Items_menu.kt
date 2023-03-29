package br.senai.sp.jandira.doetempo.HomeActivities

import br.senai.sp.jandira.doetempo.R

sealed class Items_menu(
    val icon: Int,
    val tittle: String,
    val route: String
){
    object Screen1: Items_menu(
        R.drawable.baseline_list_24,
        "Campanha", "Screen1")
    object Screen2: Items_menu(R.drawable.baseline_grid_view_24,
        "Feed", "Screen2")
    object Screen3: Items_menu(R.drawable.baseline_person_24,
        "Perfil", "Screen3")
}
