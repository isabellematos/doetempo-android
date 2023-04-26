package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.rounded.Warning
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
import br.senai.sp.jandira.doetempo.BottomBar
import br.senai.sp.jandira.doetempo.Fab
import br.senai.sp.jandira.doetempo.HomeActivities.Items_menu
import br.senai.sp.jandira.doetempo.MinFabItem
import br.senai.sp.jandira.doetempo.MultiFloatingState
import br.senai.sp.jandira.doetempo.model.PostInfo

import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FeedScreen() {


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
            profilePic = painterResource(id = R.drawable.mansmiling),
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
            profilePic = painterResource(id = R.drawable.mansmiling),
            date = "10 dez. às 14:33",
            verified = true,
            postText = "Lore epsum ragatanga arigato konichua bafome vem na minha casa bafome pra ver se eu nao encho de porrada.",
            postPhoto = painterResource(id = R.drawable.postpicexample),
            postVideo = "",
            comment = "",
            commentCount = 10,
            likeCount = 300
        ),
        PostInfo(
            name = "Nome teste3",
            profilePic = painterResource(id = R.drawable.mansmiling),
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

    var newPublication by remember {
        mutableStateOf("")
    }
    var newPublicationisError by remember {
        mutableStateOf(false)
    }

    val weightFocusRequester = FocusRequester()


//CONTENT
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color(248,248,248))
    ) {

        Box(
            modifier = Modifier

                .height(100.dp),
        ) {
            //HEADER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(70.dp)
                    .background(Color(79, 254, 199)),
            )
            {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(start = 350.dp, top = 10.dp)

                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "",
                        tint = Color.DarkGray
                    )
                }
            }
            Image(
                painter = painterResource(id = br.senai.sp.jandira.feedscreen.R.drawable.mansmiling),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(70.dp)
                    .border(
                        2.dp,
                        color = Color(79, 121, 254),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(shape = RoundedCornerShape(8.dp))
                    .align(Alignment.BottomStart)

            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = newPublication,
                onValueChange = {altPublication ->
                    if (altPublication.length == 0) {
                        newPublicationisError = true
                        altPublication
                    } else {
                        newPublicationisError = false
                    }
                    newPublication = altPublication
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .size(300.dp, 50.dp)
                    .focusRequester(weightFocusRequester),
                label = {
                    Text(
                        text = "Faça uma publicação",
                        fontWeight = FontWeight.Normal
                    )
                },
                trailingIcon = {
                    if (newPublicationisError) Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                shape = RoundedCornerShape(10.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CameraAlt,
                        modifier = Modifier.size(30.dp),
                        contentDescription = ""
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Videocam,
                        modifier = Modifier.size(35.dp),
                        contentDescription = ""
                    )
                }
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
            backgroundColor = Color(248,248,248),
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