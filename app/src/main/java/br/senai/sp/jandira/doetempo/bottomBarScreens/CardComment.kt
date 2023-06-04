package br.senai.sp.jandira.doetempo.bottomBarScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.model.Comment
import coil.compose.AsyncImage
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun ListComments(comment: Comment){

    var createdState by remember {
        mutableStateOf("")
    }

//    createdState = comment.createdAt.toString()
//
//
//    val zonedDateTime = ZonedDateTime.parse(createdState)
//    val localDate = zonedDateTime.toLocalDateTime().minusHours(3)
//    val formatterPattern = DateTimeFormatter.ofPattern("dd 'de' MMMM 'às' HH:mm", Locale("pt", "BR"))
//    val formattedDateTime = localDate.format(formatterPattern)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 12.dp, end = 12.dp)
                .size(width = 323.dp, height = 150.dp),

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
//                    if (comment.commentNgo!!.size > 0) {
//                        AsyncImage(
//                            model = comment.commentNgo[0]?.ngo?.photo_url,
//                            contentDescription = null,
//                            Modifier
//                                .size(60.dp)
//                                .padding(start = 12.dp, top = 12.dp)
//                        )
//                    } else {
//                        AsyncImage(
//                            model = comment.commentUser?.get(0)?.user?.photo_url,
//                            contentDescription = null,
//                            Modifier
//                                .size(60.dp)
//                                .padding(start = 12.dp, top = 12.dp)
//                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (!comment.commentNgo.isNullOrEmpty()) {
                                comment.commentNgo?.get(0)?.ngo?.name?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                comment.commentUser?.get(0)?.user?.name?.let {
                                    Text(
                                        text = it,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        comment.createdAt?.let {
                            Text(
                                text = it,
                                fontSize = 13.sp
                            )
                        }
                        comment.content?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(top = 12.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

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