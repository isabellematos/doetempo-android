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
import br.senai.sp.jandira.doetempo.HomeActivities.cardCampanha
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.campanha.CampanhaCall
import br.senai.sp.jandira.doetempo.services.post.PostCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

//import br.senai.sp.jandira.feedscreen.componentsFeedScreen.*

class FeedScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intentFeed = this.intent
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FeedScreen(intentFeed)
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FeedScreen(intent: Intent) {

//    val systemUi = rememberSystemUiController()
//    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.Collapsed)
    }

    val context = LocalContext.current

    val token = intent.getStringExtra("key")
    val idUser = intent.getStringExtra("id_user")
    val typeUser = intent.getStringExtra("type")

    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)

    scope.launch {
        if (token !== null && idUser !== null && typeUser !== null) {
            datastore.saveToken(token)
            datastore.saveIdUser(idUser)
            datastore.saveTypeUser(typeUser)
        }
    }

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

        val retrofit = RetrofitFactory.getRetrofit()
        val postCall = retrofit.create(PostCall::class.java)
        var callPosts = postCall.getAll()

        var postState by remember {
            mutableStateOf(listOf<Post>())
        }

        callPosts.enqueue(object : Callback<PostList> {
            override fun onResponse(call: Call<PostList>, response: Response<PostList>) {
                postState = response.body()!!.allPosts!!
            }

            override fun onFailure(call: Call<PostList>, t: Throwable) {
                Log.i("ds3mposts", t.message.toString())
            }

        })

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(postState.size) { index ->
                PostWidget(post = postState[index])
            }
        }
    }
}

