package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.HomeActivities.cardCampanha
import br.senai.sp.jandira.doetempo.HomeActivity
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.Campanha
import br.senai.sp.jandira.doetempo.model.CampanhaList
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.campanha.CampanhaCall
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import br.senai.sp.jandira.doetempo.HomeActivities.


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CampanhaScreen() {
    //CONTENT

    var userNameState by remember {
        mutableStateOf("")
    }

    var campanhasState by remember {
        mutableStateOf(CampanhaList(listOf()))
    }

    val context = LocalContext.current
    var intent = (context as HomeActivity).intent
    val nameUser = intent.getStringExtra("name")
    val token = intent.getStringExtra("key")
    val type = intent.getStringExtra("type")
    val idUser = intent.getStringExtra("id_user")


    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)

    scope.launch {
        if (token != null && idUser != null && nameUser != null && type != null) {
            datastore.saveToken(token)
            datastore.saveIdUser(idUser)
            datastore.saveNameUser(nameUser)
            datastore.saveTypeUser(type)
        }
    }

    userNameState = datastore.getNameUser.collectAsState(initial = "").value.toString()

    //Log.i("datastore", datastore.getIdUser.collectAsState(initial = "").value.toString())
    //Log.i("datastore", datastore.getNameUser.collectAsState(initial = "").value.toString())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(251, 251, 253)
            )
    ) {
        //HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Olá $userNameState!",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 22.dp, end = 10.dp)
                        .size(24.dp)
                )
            }
        }

        val retrofit = RetrofitFactory.getRetrofit()
        val campanhaCall = retrofit.create(CampanhaCall::class.java)
        val call = campanhaCall.getAll()

        var campanhasState by remember {
            mutableStateOf(listOf<Campanha>())
        }

        Log.i("listateste ", campanhasState.toString())

        call.enqueue(object : Callback<CampanhaList> {
            override fun onResponse(
                call: Call<CampanhaList>,
                response: Response<CampanhaList>
            ) {
                campanhasState = response.body()!!.campaigns!!
            }

            override fun onFailure(call: Call<CampanhaList>, t: Throwable) {
                Log.i("ds3m", t.message.toString())

                Toast.makeText(context, "Campo de campanhas vazio!", Toast.LENGTH_SHORT)
                    .show()
            }

        })

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(campanhasState.size) { index ->
                cardCampanha(campanha = campanhasState[index])
            }
        }
    }
}

