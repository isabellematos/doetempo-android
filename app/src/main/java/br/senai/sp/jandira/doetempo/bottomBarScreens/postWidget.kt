package br.senai.sp.jandira.doetempo.bottomBarScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Verified
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.model.PostInfo

@Composable
fun PostWidget(
    post: PostInfo
)
{
    val verifiedSign = br.senai.sp.jandira.doetempo.R.drawable.verifiesimbol

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
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
                    painter = post.profilePic,
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
                    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
                        if (post.verified == true) {
                            Text(
                                text = post.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.padding(3.dp))
                            Icon(imageVector = Icons.TwoTone.Verified, contentDescription = "Verified", modifier = Modifier.size(20.dp), tint = Color(69,201,165))
                        } else {
                            Text(
                                text = post.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = post.date,
                        fontSize = 13.sp
                    )
                        Text(
                            text = "${post.postText}",
                            modifier = Modifier.padding(end = 16.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                }
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.MoreHoriz,
                    contentDescription = "More Option"
                )
            }

        }
        post.postPhoto?.let {
            Image(
                painter = it,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "postphoto",
                contentScale = ContentScale.FillWidth
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.TwoTone.Favorite,
                    modifier = Modifier.size(25.dp),
                    contentDescription = "Like"
                )
            }
            Text(
                text = "${post.likeCount}",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 13.dp),
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
                text = "${post.commentCount}",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 13.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "───────────────────────────────")
        }

    }
}

@Composable
fun PostWidgetPrev() {
    PostWidget(
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
    )
    )
}