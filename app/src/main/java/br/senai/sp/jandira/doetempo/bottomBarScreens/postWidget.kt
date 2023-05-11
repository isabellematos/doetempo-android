package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Verified
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import br.senai.sp.jandira.doetempo.CampanhaDetailsActivity
import br.senai.sp.jandira.doetempo.HomeActivity
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.Address
import br.senai.sp.jandira.doetempo.model.Ong
import br.senai.sp.jandira.doetempo.model.Post
import br.senai.sp.jandira.doetempo.model.Type
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.text.DateFormat

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PostWidget(post: Post) {
    //val verifiedS
    // ign = br.senai.sp.jandira.doetempo.R.drawable.verifiesimbol


    //Log.i("", photoProfile.toString())

    var nameOngState by remember {
        mutableStateOf("")
    }

    var photoProfileOng by remember {
        mutableStateOf("")
    }

    var photoProfileUser by remember {
        mutableStateOf("")
    }

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
                .padding(top = 50.dp, start = 12.dp, end = 12.dp)
                .size(width = 323.dp, height = 200.dp),

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
                    if (post.ngo != null) {
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


                if(post.ngo!!.size > 0) {
                    nameOngState = post.ngo?.get(0)?.ngo?.name?.toString().toString()
                    photoProfileOng = post.ngo?.get(0)?.ngo?.photo_url.toString()
                }


                if(post.user!!.size > 0) {
                    nameOngState = post.user?.get(0)?.user?.name?.toString().toString()
                    photoProfileUser = post.user?.get(0)?.user?.photo_url.toString()
                    Log.i("userphoto", photoProfileUser)
                }

                Log.i("varnameong", nameOngState.toString())
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
                        post.created_at?.let {
                            Text(
                                text = it,
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                        }
                        post.content?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(top = 15.dp, start = 10.dp),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        }
                    }
                }

//                IconButton(onClick = { ) {
//                    Icon(
//                        imageVector = Icons.Filled.MoreHoriz,
//                        contentDescription = "More Option"
//                    )
//                }
//

                //post?.post_photo?.get(0)?.photoUrl?.toString()?.let { Log.i("photopost", it) }
//            var photoPost = post.post_photo?.get(0)?.photoUrl?.toString()
//
//            if (photoPost != null) {
//                if(photoPost.isNotEmpty()) {
//                    AsyncImage(
//                        model = photoPost?.toString(),
//                        contentDescription = null,
//                        Modifier
//                            .size(60.dp)
//                            .padding(start = 12.dp, top = 12.dp)
//                    )
//                }else{
//                    Log.i("paia", photoPost.toString())
//                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.TwoTone.Favorite,
                        modifier = Modifier.size(25.dp),
                        contentDescription = "Like"
                    )
                }
                Text(
                    text = "200",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 15.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Comment,
                        modifier = Modifier.size(25.dp),
                        contentDescription = "Comment"
                    )
                }
                Text(
                    text = "130",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 15.dp, end = 12.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }
        }
    }
}
