package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.EditAttributes
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
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.ong.OngCall
import br.senai.sp.jandira.doetempo.services.post.PostCall
import br.senai.sp.jandira.doetempo.services.user.UserCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProfileScreen(user = User(), ong = Ong())
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
//@Preview(showBackground = true, showSystemUi = true)
fun ProfileScreen(user: User, ong: Ong) {

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

    var attachedLink by remember() {
        mutableStateOf("")
    }


    val context = LocalContext.current
    val scrollState = rememberScrollState()

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

    if (dataType == "ONG") {

        val retrofit = RetrofitFactory.getRetrofit()
        val ongCall = retrofit.create(OngCall::class.java)

        val call = idUser?.let {
            Log.i("idUser", it)
            ongCall.getById(it)
        }

        if (call != null) {
            call.enqueue(object : Callback<Ong> {
                override fun onResponse(call: Call<Ong>, response: Response<Ong>) {
                    nameState = response.body()!!.name.toString()
                    emailState = response.body()!!.email.toString()
                    //stateState = response.body()!!.address?.postalCode.toString()
                    descriptionState = response.body()!!.description.toString()
                    photoUrlState = response.body()!!.photo_url.toString()
//                    attachedLink = response.body()!!.attachedLink?.get(0)?.attachedLink.toString()
                }

                override fun onFailure(call: Call<Ong>, t: Throwable) {
                    Log.i("ds3m", t.message.toString())
                }

            })
        }
    } else {
        val retrofit = RetrofitFactory.getRetrofit()
        val userCall = retrofit.create(UserCall::class.java)
        val call = idUser?.let { userCall.getById("Bearer $token", it) }

        if (call != null) {
            call.enqueue(object : Callback<UserDetailsProfile> {
                override fun onResponse(call: Call<UserDetailsProfile>, response: Response<UserDetailsProfile>) {
                    response.body()?.let { Log.i("user", response.body()?.user.toString()) }
                    nameState = response.body()?.user?.name.toString()
                    emailState = response.body()?.user?.email.toString()
                    // stateState = response.body()?.user?.userAddress?.address?.postalCode.toString()
                    descriptionState = response.body()?.user?.description.toString()
                    photoUrlState = response.body()?.user?.photo_url.toString()
                    // connectionState = response.body()!!.user?.count?.following.toString()
                     //attachedLink = response.body()!!.user?.attachedLink?.get(0)?.attachedLink.toString()

                }

                override fun onFailure(call: Call<UserDetailsProfile>, t: Throwable) {
                    Log.i("ds3m", t.message.toString())
                }
            })
        }
    }

//CONTENT
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
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
                    onClick = {

                        context.startActivity(Intent(context, EditDataActivity()::class.java))


                              },
                    modifier = Modifier.padding(start = 350.dp, top = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EditAttributes,
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
                    text = "2",
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
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp)
                .size(width = 300.dp, height = 900.dp)

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

                Text(
                    text = emailState,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black

                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = attachedLink,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black

            )

            Text(
                text = "Publicações",
                modifier = Modifier.padding(start = 135.dp, top = 40.dp),
                fontWeight = FontWeight.SemiBold
            )

            val retrofit = RetrofitFactory.getRetrofit()
            val postCall = retrofit.create(PostCall::class.java)
            var callPosts = postCall.getAll()

            var postState by remember {
                mutableStateOf(listOf<Post>())
            }

            callPosts.enqueue(object : Callback<PostList> {
                override fun onResponse(call: Call<PostList>, response: Response<PostList>) {
                    postState = response.body()!!.allPosts!!
                }

                override fun onFailure(call: Call<PostList>, t: Throwable) {
                    Log.i("ds3mposts", t.message.toString())
                }

            })

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
            ) {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(postState.size) { index ->
                        PostWidget(post = postState[index])
                    }
                }

            }
        }
    }
}



