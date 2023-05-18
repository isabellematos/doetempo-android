package br.senai.sp.jandira.doetempo.HomeActivities

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.senai.sp.jandira.doetempo.bottomBarScreens.CampanhaScreen
import br.senai.sp.jandira.doetempo.bottomBarScreens.FeedScreen
import br.senai.sp.jandira.doetempo.bottomBarScreens.ProfileScreen
import br.senai.sp.jandira.doetempo.model.*

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Items_menu.Screen1.route
    ) {
        composable(Items_menu.Screen1.route) {
            CampanhaScreen()
        }
        composable(Items_menu.Screen2.route) {
            FeedScreen(intent = Intent())
        }
        composable(Items_menu.Screen3.route) {
            ProfileScreen()
        }
    }
}