package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import br.senai.sp.jandira.doetempo.model.*
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable

fun PostWidget(post: Post) {

    var countLike by remember {
        mutableStateOf(0)
    }

    var nameOngState by remember {
        mutableStateOf("")
    }

    var photoPostState by remember {
        mutableStateOf("")
    }

    var photoProfileOng by remember {
        mutableStateOf("")
    }

    var photoProfileUser by remember {
        mutableStateOf("")
    }

    val scrollState = rememberScrollState()

    val zonedDateTime = ZonedDateTime.parse(post.created_at.toString())
    val localDate = zonedDateTime.toLocalDateTime().minusHours(3)
    val formatterPattern =
        DateTimeFormatter.ofPattern("dd 'de' MMMM 'às' HH:mm", Locale("pt", "BR"))
    val formattedDateTime = localDate.format(formatterPattern)

    var nameUserState by remember {
        mutableStateOf("")
    }

    var context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                //.requiredHeightIn(min = 200.dp, max = 750.dp)
                .padding(top = 50.dp, start = 12.dp, end = 12.dp),
            backgroundColor = Color(244, 244, 244),
            shape = RoundedCornerShape(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    if (post.ngo!!.size > 0) {
                        AsyncImage(
                            model = photoProfileOng,
                            contentDescription = null,
                            Modifier
                                .size(60.dp)
                                .padding(start = 12.dp, top = 12.dp)
                        )
                    } else {
                        AsyncImage(
                            model = photoProfileUser,
                            contentDescription = null,
                            Modifier
                                .size(60.dp)
                                .padding(start = 12.dp, top = 12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                // post.ngo?.toString()?.let { Log.i("dadosong", it) }
                // post.user?.toString()?.let { Log.i("dadosuser", it) }


                if (post.ngo!!.size > 0) {
                    nameOngState = post.ngo?.get(0)?.ngo?.name?.toString().toString()
                    photoProfileOng = post.ngo?.get(0)?.ngo?.photo_url.toString()
                }


                if (post.user!!.size > 0) {
                    nameOngState = post.user?.get(0)?.user?.name?.toString().toString()
                    photoProfileUser = post.user?.get(0)?.user?.photo_url.toString()
                }


                if (post.post_photo!!.size > 0) {
                    photoPostState = post.post_photo!![0]?.photoUrl.toString()
                    //Log.i("hmm", post.post_photo!![0]?.photoUrl.toString())

                }


//                nameUserState = post.user?.get(0)?.user?.name?.toString().toString()
//                Log.i("nomeuser", post.user?.get(0)?.user?.name.toString())
                //post.ngo?.get(0)?.ngo?.let { Log.i("nomeong", it.name) }
                // Log.i("nomeong",post.ngo?.get(0)?.name.toString())

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (post.ngo != null) {
                            Text(
                                text = nameOngState,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = nameUserState,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.padding(3.dp))
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        formattedDateTime.toString()?.let {
                            Text(
                                text = it,
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                        }
                        post.content?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(top = 15.dp, start = 5.dp),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        }

                    }
                }
            }
//            if (post.post_photo!!.isNotEmpty()) {
//                AsyncImage(
//                    model = photoPostState,
//                    contentDescription = "imagem dos posts",
//                    //Modifier
//                        //.size(height = 220.dp, width = 200.dp),
//                       // .padding(start = 15.dp, top = 500.dp),
//                    contentScale = ContentScale.Fit
//                )
//            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(onClick = {
                countLike = post.count?.postLikes!!.toInt() + 1

            })
            {
                Icon(
                    imageVector = Icons.TwoTone.Favorite,
                    modifier = Modifier.size(25.dp),
                    contentDescription = "Like",
                    tint = Color.Red
                )
            }
            Text(
                text = countLike.toString(),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 15.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            IconButton(onClick = {
                val newActivity =
                    Intent(
                        context,
                        CommentsActivity::class.java
                    ).putExtra("idPost", post.id)

                ContextCompat.startActivity(context, newActivity, Bundle.EMPTY)

            })
            {
                Icon(
                    imageVector = Icons.Outlined.Comment,
                    modifier = Modifier.size(25.dp),
                    contentDescription = "Comment"
                )
            }
            Text(
                text = post.count?.comment.toString(),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 15.dp, end = 12.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        }
    }
}


