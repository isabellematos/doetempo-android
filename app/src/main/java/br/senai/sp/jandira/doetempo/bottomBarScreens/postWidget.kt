package br.senai.sp.jandira.doetempo.bottomBarScreens

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import br.senai.sp.jandira.doetempo.CampanhaDetailsActivity
import br.senai.sp.jandira.doetempo.model.Post
import coil.compose.AsyncImage
import java.text.DateFormat

@Composable
fun PostWidget(post: Post) {
    //val verifiedS
    // ign = br.senai.sp.jandira.doetempo.R.drawable.verifiesimbol


//    val photoProfile = post.ngo?.photo_ngo.toString()
//    Log.i("photo1", photoProfile)
//

//    val photoPost = post.post_photo
//    if (photoPost != null) {
//        Log.i("photo2", photoPost.toString())
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 12.dp, end = 12.dp)
                .size(width = 323.dp, height = 190.dp),

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
                    Image(
                        painter = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.mansmiling),
                        contentDescription = "logo",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(start = 12.dp, top = 12.dp)
                            .border(
                                2.dp,
                                color = Color(79, 121, 254),
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
//                    AsyncImage(
//                        model = photoProfile,
//                        contentDescription = null,
//                        Modifier
//                            .size(60.dp)
//                            .padding(start = 12.dp, top = 12.dp)
//                            .border(
//                                2.dp,
//                                color = Color(79, 121, 254),
//                                shape = RoundedCornerShape(8.dp)
//                            )
                    Spacer(modifier = Modifier.width(10.dp))
                }

//                    Column(
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                        if (post.ngo !== null) {
//                            post.ngo?.let {
//                                it[0]?.let { it1 ->
//                                    Text(
//                                        text = it1.name,
//                                        fontSize = 20.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                }
//                            }
//                        }else
//                            post.user?.let {
//                                it[0]?.let { it1 ->
//                                    Text(
//                                        text = it1.name,
//                                        fontSize = 20.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                }
                        //}
                    //    Spacer(modifier = Modifier.padding(3.dp))
                    //}
                    post.created_at?.let {
                        Text(
                            text = it,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }
                    post.content?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(end = 16.dp, top = 20.dp),
                            fontSize = 15.sp,
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
//            AsyncImage(
//                model = photoPost,
//                contentDescription = null,
//                Modifier
//                    .size(20.dp)
//                    .padding(start = 12.dp, top = 12.dp)
//            )
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




//@Composable
//fun PostWidgetPrev() {
//    PostWidget(
//        PostInfo(
//        name = "Nome teste",
//        profilePic = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.mansmiling),
//        date = "27 dez. às 14:33",
//        verified = false,
//        postText = "Lore epsum ragatanga arigato konichua bafome vem na minha casa bafome pra ver se eu nao encho de porrada.",
//        postPhoto = null,
//        postVideo = "",
//        comment = "",
//        commentCount = 10,
//        likeCount = 300
//    )
//    )
//}