package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.twotone.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.doetempo.HomeActivity
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.User
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
//@Preview(showBackground = true, showSystemUi = true)
fun ProfileScreen() {

    var userNameState by remember {
        mutableStateOf("")
    }

    var userEmailState by remember {
        mutableStateOf("")
    }

    val weightFocusRequester = FocusRequester()
    val systemUi = rememberSystemUiController()
    val navController = rememberNavController()


    val context = LocalContext.current

    val intent = (context as HomeActivity).intent
    var nameUser = intent.getStringExtra("name")
    var token = intent.getStringExtra("key")
    var idUser = intent.getStringExtra("id_user")
    var emailUser = intent.getStringExtra("email")

    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)


    scope.launch {
        if (token != null && idUser != null && nameUser != null && emailUser != null ) {
            datastore.saveToken(token)
            datastore.saveIdUser(idUser)
            datastore.saveNameUser(nameUser)
            datastore.saveEmail(emailUser)

        }
    }

    userNameState = datastore.getNameUser.collectAsState(initial = "").value.toString()
    userEmailState = datastore.getEmail.collectAsState(initial = "").value.toString()


//CONTENT
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )
    ) {
        Box(
            modifier = Modifier.height(100.dp)
        ) {
            //HEADER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(70.dp)
                    .background(Color(79, 254, 199))
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(start = 350.dp, top = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "",
                        tint = Color.DarkGray
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.mansmiling),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(70.dp)
                    .border(
                        2.dp, color = Color(79, 121, 254),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(shape = RoundedCornerShape(8.dp))
                    .align(Alignment.BottomStart)
            )
            Text(
                text = userNameState,
                modifier = Modifier
                    .padding(top = 10.dp, start = 50.dp)
                    .align(Alignment.BottomCenter),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "{XXX}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(79, 121, 254)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Conexões",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp)
        ) {
            Text(
                text = "Sobre:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus vel pulvinar orci. Etiam augue urna, eleifend vitae dolor ut, varius aliquam est. Pellentesque commodo vehicula euismod. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.",
                modifier = Modifier.padding(end = 10.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.TwoTone.PersonPinCircle,
                    modifier = Modifier
                        .size(30.dp),
                    contentDescription = "PinDrop",
                    tint = Color(79, 121, 254)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = userEmailState,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black

                )
            }

            Text(
                text = "Publicações",
                modifier = Modifier.padding(start = 135.dp, top = 40.dp),
                fontWeight = FontWeight.SemiBold
            )


            Card(
                modifier = Modifier
                    .padding(end = 20.dp, top = 16.dp)
                    .fillMaxWidth()
                    .border(
                        BorderStroke(0.5.dp, color = Color.Black),
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clip(shape = RoundedCornerShape(30.dp))
                    .size(width = 320.dp, height = 160.dp)
                    .background(Color(248, 248, 248))
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
                            Text(
                                text = "Marcelo do grau",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "26 jan 2000",
                                fontSize = 13.sp
                            )
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp)) {
                                Text(text = "skdjfnksdfnsdkjfhsdkfjhsdifh")
                            }

                        }
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreHoriz,
                            contentDescription = "More Option"
                        )
                    }

                }
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
                    text = "xxx",
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
                    text = "xxx",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 13.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }
        }
    }
}

