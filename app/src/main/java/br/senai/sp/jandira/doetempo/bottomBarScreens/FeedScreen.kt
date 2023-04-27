package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.doetempo.HomeActivities.Items_menu
import br.senai.sp.jandira.doetempo.model.PostInfo
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.doetempo.*
import br.senai.sp.jandira.doetempo.components.bottom
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
//import br.senai.sp.jandira.feedscreen.componentsFeedScreen.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.storage.FirebaseStorage

class FeedScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FeedScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FeedScreen(viewModel: CreateCampanhaViewModel = viewModel()) {

    val systemUi = rememberSystemUiController()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.Collapsed)
    }
    val items = listOf(
        MinFabItem(
            label = "Nova Campanha",
            identifier = ""
        ),
        MinFabItem(
            label = "Nova publicação",
            identifier = ""
        ),
        MinFabItem(
            label = "Achar vagas",
            identifier = ""
        ),
    )

    val navigationItem = listOf(
        Items_menu.Screen1,
        Items_menu.Screen2,
        Items_menu.Screen3
    )


    val users = listOf(
        PostInfo(
            name = "Nome teste",
            profilePic = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.mansmiling),
            date = "27 dez. às 14:33",
            verified = false,
            postText = "Lore epsum ragatanga arigato konichua bafome vem na minha casa bafome pra ver se eu nao encho de porrada.",
            postPhoto = null,
            postVideo = "",
            comment = "",
            commentCount = 10,
            likeCount = 300
        ),
        PostInfo(
            name = "Nome teste2",
            profilePic = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.mansmiling),
            date = "10 dez. às 14:33",
            verified = true,
            postText = "Lore epsum ragatanga arigato konichua bafome vem na minha casa bafome pra ver se eu nao encho de porrada.",
            postPhoto = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.postpicexample),
            postVideo = "",
            comment = "",
            commentCount = 10,
            likeCount = 300
        ),
        PostInfo(
            name = "Nome teste3",
            profilePic = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.mansmiling),
            date = "27 dez. às 07:55",
            verified = false,
            postText = "",
            postPhoto = null,
            postVideo = "https://www.youtube.com/watch?v=H3uS1Mn_t_k",
            comment = "",
            commentCount = 10,
            likeCount = 300
        )
    )


//CONTENT

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    context.startActivity(Intent(context, NewPostActivity()::class.java))
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 1.dp, end = 50.dp),
                )
                Text(
                    text = "Faça uma publicação!",
                    modifier = Modifier.padding(start = 10.dp),
                    fontWeight = FontWeight.Normal
                )
            }
        }


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
            backgroundColor = Color(248, 248, 248),
            isFloatingActionButtonDocked = true
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                item { Divider() }
                items(users) { post ->
                    PostWidget(post = post)
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

