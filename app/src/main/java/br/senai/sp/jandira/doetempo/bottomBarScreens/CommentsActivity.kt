package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.post.PostCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var intentComment = this.intent
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HudComentarios(intentComment)
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HudComentarios(intent: Intent) {

    var commentState by remember {
        mutableStateOf("")
    }

    var commentStateResult by remember {
        mutableStateOf("")
    }

    var result by remember {
        mutableStateOf(listOf(Post()))
    }

    var commentIsError by remember {
        mutableStateOf(false)
    }

    var commentState2 by remember {
        mutableStateOf(listOf(Comment()))
    }

    var searchQuery by remember {
        mutableStateOf(Post().id)
    }
    var context = LocalContext.current

    val idPost = intent.getStringExtra("idPost")
    Log.i("idpost", idPost.toString())
    val datastore = DataStoreAppData(context)
    val token = datastore.getToken.collectAsState(initial = "").value.toString()

    val retrofit = RetrofitFactory.getRetrofit()
    val postCall = retrofit.create(PostCall::class.java)
    var callComments = postCall.getAll()

    callComments.enqueue(object : Callback<PostList> {
        override fun onResponse(call: Call<PostList>, response: Response<PostList>) {

            Log.i("comments", response.body()!!.allPosts?.get(0)?.comment!!.toString())
            commentState2 = response.body()!!.allPosts?.get(0)?.comment!!

//                 result = response.body()!!.allPosts?.filter { post ->
//                     post.id?.contains(post.toString())
//
//                     if (post.id == idPost ) {
//                         return post
//                     }
//
//                 }!!
//
//            result[0].comment.toString()

        }

        override fun onFailure(call: Call<PostList>, t: Throwable) {
            Log.i("ds3m", t.message.toString())
        }
    })







    val retrofit1 = RetrofitFactory.getRetrofit()
    val postCall1 = retrofit1.create(PostCall::class.java)
    var callCommentsId = result.let { postCall1.getById("Bearer $token", it) }

    if (result != null) {
        if (callCommentsId != null) {
            callCommentsId.enqueue(object : Callback<PayloadPost> {
                override fun onResponse(call: Call<PayloadPost>, response: Response<PayloadPost>) {

                   Log.i( "result", result[0].comment.toString())

                    commentStateResult = result[0].comment.toString()
                    Log.i("comments", response.body()?.payload.toString() )
                    commentState2 = response.body()!!.payload?.comment!!

                }

                override fun onFailure(call: Call<PayloadPost>, t: Throwable) {
                    Log.i("ds3m", t.message.toString())
                }
            })
        }
    }




    val scrollState = rememberScrollState()

    //Controla o foco
    val weightFocusRequester = FocusRequester()


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        //HEADER
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Botão voltar",
                    tint = Color(79, 121, 254),
                    modifier = Modifier.clickable {
                        context.startActivity(Intent(context, FeedScreenActivity::class.java))
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Comentários",
                        color = Color(79, 121, 254),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            Text(text = "───────────────────────────────")
        }
        //MAIN
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                //Comentário
                OutlinedTextField(
                    value = commentState,
                    onValueChange = { newComment ->
                        if (newComment.length == 0) {
                            commentIsError = true
                            newComment
                        } else {
                            commentIsError = false
                        }
                        commentState = newComment
                    },
                    modifier = Modifier
                        .padding(start = 5.dp, bottom = 30.dp)
                        .size(width = 300.dp, height = 60.dp)
                        .focusRequester(weightFocusRequester),
                    trailingIcon = {
                        if (commentIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    label = {
                        Text(
                            text = "Faça um comentário",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                )
                IconButton(onClick = {

                    val contact = SendComment(
                        content = commentState,
                    )

                    val retrofit = RetrofitFactory.getRetrofit()
                    val postCall = retrofit.create(PostCall::class.java)
                    val call = idPost?.let { postCall.saveComment("Bearer $token", it, contact) }

                    if (call != null) {
                        call.enqueue(object : Callback<ResponseComment> {
                            override fun onResponse(
                                call: Call<ResponseComment>,
                                response: Response<ResponseComment>
                            ) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Comentário feito com sucesso!",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                                Log.i("comentario", response.body()!!.toString())
                            }

                            override fun onFailure(call: Call<ResponseComment>, t: Throwable) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 15.dp)
                            .size(40.dp),
                        tint = Color(79, 121, 254),
                        contentDescription = "Enviar Comentário"
                    )
                }
            }
        }
//        var commentState by remember {
//            mutableStateOf(listOf<Comment>())
//        }


//        var commentState2 = commentState1?.allPosts?.get(0)?.comment

            var commentState1 = PostList(allPosts = listOf())

            LazyColumn(modifier = Modifier.padding(16.dp)) {
                commentState2?.let {
                    items(it.size) { index ->
                        ListComments(comment = commentState2!![index])
                    }
                }
            }
    }
}



