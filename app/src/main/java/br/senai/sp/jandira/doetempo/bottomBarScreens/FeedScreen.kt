package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.platform.LocalContext
import br.senai.sp.jandira.doetempo.*
import br.senai.sp.jandira.doetempo.CampanhaComponents.datas
import br.senai.sp.jandira.doetempo.HomeActivities.cardCampanha
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.campanha.CampanhaCall
import br.senai.sp.jandira.doetempo.services.post.PostCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

//import br.senai.sp.jandira.feedscreen.componentsFeedScreen.*

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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FeedScreen() {

//    val systemUi = rememberSystemUiController()
//    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.Collapsed)
    }

    val context = LocalContext.current


    var intent = (context as HomeActivity).intent
    val token = intent.getStringExtra("key")
    val idUser = intent.getStringExtra("id_user")
    val nameUser = intent.getStringExtra("name")
    val typeUser = intent.getStringExtra("type")

    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)

    scope.launch {
        if (token !== null && idUser !== null && nameUser !== null  && typeUser !== null) {
            datastore.saveToken(token)
            datastore.saveIdUser(idUser)
            datastore.saveNameUser(nameUser)
            datastore.saveTypeUser(typeUser)
        }
    }

//    val users = listOf(
//        post.tbl_ngo?.let {
//            post.content?.let { it1 ->
//                PostInfo(
//                    name = it.name,
//                    profilePic = photoProfile,
//                    date = post.created_at,
//                    verified = false,
//                    postText = it1,
//                    postPhoto = post.post_photo,
//                    postVideo = "",
//                    comment = "",
//                    commentCount = 10,
//                    likeCount = 300
//                )
//            }
//        },
//    )

//CONTENT

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

//        Scaffold(
//            scaffoldState = scaffoldState,
//            floatingActionButton = {
//            },
//            backgroundColor = Color(248, 248, 248),
//            isFloatingActionButtonDocked = true
//        ) { innerPadding ->
//            LazyColumn(
//                modifier = Modifier.padding(innerPadding)
//            ) {
//                item { Divider() }
//                items(users) { post ->
//                    if (post != null) {
//                        PostWidget(post = Post())
//                    }
//                    Spacer(modifier = Modifier.height(30.dp))
//                }
//            }
//        }

        val retrofit = RetrofitFactory.getRetrofit()
        val postCall = retrofit.create(PostCall::class.java)
        var callPosts = postCall.getAll()

        var postState by remember {
            mutableStateOf(listOf<Post>())
        }

        callPosts.enqueue(object : Callback<PostList> {
            override fun onResponse(call: Call<PostList>, response: Response<PostList>) {
             postState = response.body()!!.allPosts
            }

            override fun onFailure(call: Call<PostList>, t: Throwable) {
                Log.i("ds3m", t.message.toString())
            }

        })

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(postState.size) { index ->
                PostWidget(post = postState[index])
            }
        }
    }
}

