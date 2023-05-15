package br.senai.sp.jandira.doetempo.bottomBarScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.model.Comment
import coil.compose.AsyncImage

@Composable
fun ListComments(comment: Comment){


    if (comment.commentNgo!!.size > 0) {

    }


    if (comment.commentUser!!.size > 0) {

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 12.dp, end = 12.dp)
                .size(width = 323.dp, height = 250.dp),

            backgroundColor = Color(244, 244, 244),
            shape = RoundedCornerShape(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    if (comment.commentNgo!!.size > 0) {
                        AsyncImage(
                            model = comment.commentNgo[0]?.ngo?.photo_url,
                            contentDescription = null,
                            Modifier
                                .size(60.dp)
                                .padding(start = 12.dp, top = 12.dp)
                        )
                    } else {
                        AsyncImage(
                            model = comment.commentUser[0]?.user?.photo_url,
                            contentDescription = null,
                            Modifier
                                .size(60.dp)
                                .padding(start = 12.dp, top = 12.dp)
                        )
                    }
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
                            if (comment.commentNgo[0].ngo.name != null) {
                                comment.commentNgo[0]?.ngo?.name?.let {
                                    Text(
                                        text = it,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                comment.commentUser[0]?.user?.name?.let {
                                    Text(
                                        text = it,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Text(
                            text = comment.createdAt,
                            fontSize = 13.sp
                        )
                        Text(
                            text = comment.content,
                            modifier = Modifier.padding(top = 12.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
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
//        Column(
//            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "────────────────────────────────────────",
//                color = Color.LightGray
//            )
//        }
            }
        }
    } //nao apaga esse
}