package br.senai.sp.jandira.doetempo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.Ong
import br.senai.sp.jandira.doetempo.model.SupportedCampaignsUser
import br.senai.sp.jandira.doetempo.model.User
import br.senai.sp.jandira.doetempo.model.UserDetailsProfile
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.user.UserCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupportedCampaignsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SupportedCampaignScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun SupportedCampaignScreen() {

    var supportedCampaignsTitle by remember {
        mutableStateOf("")
    }

    var supportedCampaignsDescripition by remember {
        mutableStateOf("")
    }

    var supportedCampaignsOng by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val intent = (context as HomeActivity).intent
    var token = intent.getStringExtra("key")
    var idUser = intent.getStringExtra("id_user")

    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)


    scope.launch {
        if (token != null && idUser != null ) {
            datastore.saveToken(token)
            datastore.saveIdUser(idUser!!)
        }
    }

    idUser = datastore.getIdUser.collectAsState(initial = "").value.toString()

    val retrofit = RetrofitFactory.getRetrofit()
    val userCall = retrofit.create(UserCall::class.java)
    val call = idUser?.let { userCall.getById("Bearer $token", it) }

    if (call != null) {
        call.enqueue(object : Callback<UserDetailsProfile> {
            override fun onResponse(call: Call<UserDetailsProfile>, response: Response<UserDetailsProfile>) {
                response.body()?.let { Log.i("user", response.body()?.user.toString()) }
                supportedCampaignsTitle = response.body()?.user?.supported_campaigns?.get(0)?.campaign?.title.toString()
                supportedCampaignsDescripition = response.body()?.user?.supported_campaigns?.get(0)?.campaign?.description.toString()
                supportedCampaignsOng = response.body()?.user?.supported_campaigns?.get(0)?.campaign?.ngo?.name.toString()
                // stateState = response.body()?.user?.userAddress?.address?.postalCode.toString()
//                descriptionState = response.body()?.user?.description.toString()
//                photoUrlState = response.body()?.user?.photo_url.toString()
                // connectionState = response.body()!!.user?.count?.following.toString()
                //attachedLink = response.body()!!.user?.attachedLink?.get(0)?.attachedLink.toString()

            }

            override fun onFailure(call: Call<UserDetailsProfile>, t: Throwable) {
                Log.i("ds3m", t.message.toString())
            }
        })
    }


    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp)
            .fillMaxSize(1f)
    ) {
        Text(
            text = "Campanhas nas quais você se inscreveu: ",
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Text(
            text = supportedCampaignsTitle,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(end = 50.dp),
            color = Color.Black,
            fontSize = 17.sp
        )
        Text(
            text = supportedCampaignsDescripition,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(end = 50.dp),
            color = Color.Black,
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = "Realizada pela Ong",
            modifier = Modifier.padding(top = 52.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        Text(
            text = supportedCampaignsOng,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(end = 50.dp),
            color = Color.Black,
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.padding(8.dp))

    }
}




