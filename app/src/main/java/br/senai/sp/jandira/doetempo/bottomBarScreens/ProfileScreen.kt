package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.doetempo.HomeActivity
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.Ong
import br.senai.sp.jandira.doetempo.model.User
import br.senai.sp.jandira.doetempo.model.UserDetails
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.ong.OngCall
import br.senai.sp.jandira.doetempo.services.user.UserCall
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    var photoUrlState by remember {
        mutableStateOf("")
    }

    var nameState by remember {
        mutableStateOf("")
    }

    var emailState by remember() {
        mutableStateOf("")
    }

    var stateState by remember() {
        mutableStateOf("")
    }

    var descriptionState by remember() {
        mutableStateOf("")
    }

    var connectionState by remember() {
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
    var dataType = intent.getStringExtra("type")
    val typeUserOng = intent.getStringExtra("ONG")
    val typeUser = intent.getStringExtra("USER")

    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)


    scope.launch {
        if (token != null && idUser != null && nameUser != null && emailUser != null && dataType != null && typeUserOng != null && typeUser != null) {
            datastore.saveToken(token)
            datastore.saveIdUser(idUser!!)
            datastore.saveNameUser(nameUser)
            datastore.saveEmail(emailUser)
            datastore.saveTypeUser(dataType!!)
        }
    }

    idUser = datastore.getIdUser.collectAsState(initial = "").value.toString()
    dataType = datastore.getTypeUser.collectAsState(initial = "").value.toString()

    if(dataType == "ONG") {

        val retrofit = RetrofitFactory.getRetrofit()
        val ongCall = retrofit.create(OngCall::class.java)

        val call = idUser?.let {
            Log.i("idUser", it)
            ongCall.getById(it) }

        if (call != null) {
            call.enqueue(object : Callback<Ong> {
                override fun onResponse(call: Call<Ong>, response: Response<Ong>) {
                    nameState = response.body()!!.name
                    emailState = response.body()!!.email.toString()
                    stateState = response.body()!!.address?.postalCode.toString()
                    descriptionState = response.body()!!.description.toString()
                    photoUrlState = response.body()!!.photo_url.toString()
                    connectionState = response.body()!!.


                }

                override fun onFailure(call: Call<Ong>, t: Throwable) {
                    Log.i("ds3m", t.message.toString())
                }

            })
        }
    }
    else {
        val retrofit = RetrofitFactory.getRetrofit()
        val userCall = retrofit.create(UserCall::class.java)
        val call = idUser?.let { userCall.getById("Bearer $token", it) }

        if (call != null) {
            call.enqueue(object : Callback<UserDetails> {
                override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                    response.body()?.let { Log.i("user", response.body()?.user.toString()) }
                    nameState = response.body()?.user?.name.toString()
                    emailState = response.body()?.user?.email.toString()
                    stateState = response.body()?.user?.address?.postalCode.toString()
                    descriptionState = response.body()?.user?.description.toString()
                    photoUrlState = response.body()?.user?.photo_url.toString()

                }

                override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                    Log.i("ds3m", t.message.toString())
                }
            })
        }
    }


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
                    .size(80.dp)
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
            AsyncImage(
                model = photoUrlState,
                contentDescription = null,
                Modifier
                    .size(90.dp)
                    .padding(start = 20.dp, top = 12.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .align(Alignment.BottomStart)
            )
//            Image(
//                painter = painterResource(id = R.drawable.mansmiling),
//                contentDescription = "",
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .size(70.dp)
//                    .border(
//                        2.dp, color = Color(79, 121, 254),
//                        shape = RoundedCornerShape(8.dp)
//                    )
//                    .clip(shape = RoundedCornerShape(8.dp))
//                    .align(Alignment.BottomStart)
//            )
            Text(
                text = nameState,
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
                    text = ,
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
                text = descriptionState,
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
                    text = emailState,
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

