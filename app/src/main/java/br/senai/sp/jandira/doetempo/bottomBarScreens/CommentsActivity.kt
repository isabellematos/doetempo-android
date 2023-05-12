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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import br.senai.sp.jandira.doetempo.HomeActivity
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.post.PostCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var intentComment  = this.intent
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
fun HudComentarios(intent: Intent, comment: Comment) {

    var commentState by remember {
        mutableStateOf("")
    }
    var commentIsError by remember {
        mutableStateOf(false)
    }

    var context = LocalContext.current


    val token = intent.getStringExtra("key")
    val idPost = intent.getStringExtra("idPost")
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
                    modifier = Modifier.clickable { }
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
                call.enqueue(object: Callback<ResponseComment>{
                    override fun onResponse(
                        call: Call<ResponseComment>,
                        response: Response<ResponseComment>
                    ) {
                        TODO("Not yet implemented")
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


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mansmiling),
                        modifier = Modifier
                            .border(
                                2.dp,
                                color = Color(79, 121, 254),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .size(45.dp)
                            .clip(CircleShape),
                        contentDescription = "Profile Pic"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
//                        if (post.verified == true) {
//                            Text(
//                                text = post.name,
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                            Spacer(modifier = Modifier.padding(3.dp))
//                            Icon(imageVector = Icons.TwoTone.Verified, contentDescription = "Verified", modifier = Modifier.size(20.dp), tint = Color(69,201,165))
//                        } else {
                            Text(
                                text = "Miguel Santos Souza",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "3 dias atrás",
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Adorei muito esse dia! Pudemos ajudar muitos animais que estavam precisando de nossa ajuda!",
                            modifier = Modifier.padding(top = 12.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 20.dp)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.TwoTone.Favorite,
                    modifier = Modifier.size(25.dp),
                    contentDescription = "Like"
                )
            }
            Text(
                text = "xxx",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 14.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "────────────────────────────────────────",
                color = Color.LightGray
            )
        }
    } //nao apaga esse
}

