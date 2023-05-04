package br.senai.sp.jandira.doetempo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.doetempo.HomeActivities.Items_menu
import br.senai.sp.jandira.doetempo.HomeActivities.NavigationHost
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(251, 251, 253)
                ) {
                    val systemUi = rememberSystemUiController()
                    SideEffect {
                        systemUi.setStatusBarColor(
                            color = Color(251, 251, 253, 0),
                            darkIcons = true
                        )
                    }
                    MainScreen()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val savedToken = DataStoreAppData(context = context).getToken.collectAsState(initial = "")

    //onClick = {context.startActivity(Intent(context, CadastroUserActivity::class.java)) },

    val items = listOf(
        MinFabItem(
            label = "Nova Campanha",
//            onClickAction =  clickAction(context, Intent(context, CreateCampanha::class.java)), //context.startActivity(Intent(context, CreateCampanha::class.java)),
            identifier = ""
        ),
        MinFabItem(
            label = "Nova Publicação",
            identifier = "",
//            onClickAction = clickAction(context, Intent(context, CreateCampanha::class.java))
        ),
        MinFabItem(
            label = "Achar vagas",
            identifier = "",
//            onClickAction = clickAction(context, Intent(context, CreateCampanha::class.java))
        ),
    )

    val navigationItem = listOf(
        Items_menu.Screen1,
        Items_menu.Screen2,
        Items_menu.Screen3
    )

    var multiFloatingState by remember { mutableStateOf(MultiFloatingState.Collapsed) }
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomBar(navController, navigationItem) },
        floatingActionButton = {
            Fab(
                multiFloatingState = multiFloatingState,
                onMultiFabStateChange = {
                    multiFloatingState = it
                 },
                items = items
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true
    ) {
        NavigationHost(navController)
    }
}


@Composable
fun currentRoute(navController: NavHostController): String? {
    val entrada by navController.currentBackStackEntryAsState()
    return entrada?.destination?.route
}

@Composable
fun BottomBar(
    navController: NavHostController,
    menu_items: List<Items_menu>
) {
    BottomAppBar(
        backgroundColor = Color(157, 231, 253),
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        )
    ) {
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 65.dp, 0.dp),
            backgroundColor = Color(157, 231, 253),
            elevation = 0.dp
        ) {
            val currentRoute = currentRoute(navController = navController)
            menu_items.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = { navController.navigate(item.route) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.tittle
                        )
                    },
                    label = {
                        Text(
                            text = item.tittle
                        )
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}

@Composable
fun Fab(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    FloatingActionButton(
        onClick = {
            scope.launch {
                scaffoldState.snackbarHostState
                    .showSnackbar(
                        "Esta opcao esta indisponivel temporariamente",
                        actionLabel = "Aceitar",
                        duration = SnackbarDuration.Indefinite
                    )
            }
        },
        backgroundColor = Color(157, 231, 253)

    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Menu"
        )
    }
}





























