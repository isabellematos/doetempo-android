package br.senai.sp.jandira.doetempo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.CampanhaComponents.cardAlbum
import br.senai.sp.jandira.doetempo.CampanhaComponents.cardCategoria
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.campanha.CampanhaCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CampanhaDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val systemUi = rememberSystemUiController()
                    SideEffect {
                        systemUi.setStatusBarColor(color = Color(79, 121, 254), darkIcons = true)
                    }
                    AboutCampanha(
                        campanha = Campanha(
                            id = "",
                            title = "",
                            description = "",
                            begin_date = "",
                            end_date = "",
                            home_office = false,
                            how_to_contribute = "",
                            prerequisites = "",
                            tbl_ngo = Ong(
                                id = "",
                                name = "",
                                email = "",
                                password = "",
                                cnpj = "",
                                foundationDate = "",
                                address = Address(postalCode = "", number = "", complement = ""),
                                id_type = "",
                                description = ""
                            ),
                            tbl_campaign_address = Address(
                                postalCode = "",
                                number = "",
                                complement = ""
                            )
                        )
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AboutCampanha(campanha: Campanha) {

    val retrofit = RetrofitFactory.getRetrofit()
    val campanhaCall = retrofit.create(CampanhaCall::class.java)


    var titleState by remember {
        mutableStateOf("")
    }

    var ongState by remember {
        mutableStateOf("")
    }

    var idOngState by remember {
        mutableStateOf("")
    }

    var addressState by remember {
        mutableStateOf("")
    }

    var idState by remember {
        mutableStateOf("")
    }

    var descriptionState by remember {
        mutableStateOf("")
    }

    var beginDateState by remember {
        mutableStateOf("")
    }

    var endDateState by remember {
        mutableStateOf("")
    }

    var homeOfficeState by remember {
        mutableStateOf(false)
    }

    var howToContributeState by remember {
        mutableStateOf("")
    }

    var prerequisitesState by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    var intent = (context as CampanhaDetailsActivity).intent
    idState = intent.getStringExtra("id").toString()
    Log.i("ds3m", idState)


    if (idState != "") {

        val call = campanhaCall.getById(idState)
        call.enqueue(object : Callback<CampanhaDetalhes> {

            override fun onResponse(
                call: Call<CampanhaDetalhes>,
                response: Response<CampanhaDetalhes>
            ) {
                titleState = response.body()!!.campaigns.title.toString()
                descriptionState = response.body()!!.campaigns.description.toString()
                beginDateState = response.body()!!.campaigns.begin_date.toString()
                endDateState = response.body()!!.campaigns.end_date.toString()
                homeOfficeState = response.body()!!.campaigns.home_office == true
                howToContributeState = response.body()!!.campaigns.how_to_contribute.toString()
                prerequisitesState = response.body()!!.campaigns.prerequisites.toString()
                ongState = response.body()!!.campaigns.tbl_ngo?.name ?: ""
                idOngState = response.body()!!.campaigns.tbl_ngo?.id ?: ""
            }

            override fun onFailure(call: Call<CampanhaDetalhes>, t: Throwable) {
                Log.i("ds3m", t.message.toString())
            }

        })

    } else {
        Log.i("ds3m", "erro: id vazio")

    }


    val scrollState = rememberScrollState()

    var colorTint by remember {
        mutableStateOf(Color.White)
    }


    //Content
    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        //Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomEnd = 70.dp, bottomStart = 70.dp),
            backgroundColor = Color(79, 121, 254)

        ) {
            Column(
                modifier = Modifier
                    .background(Color(79, 121, 254))
                    .height(150.dp)
            ) {

                //Imagem e texto
                Row(
                    modifier = Modifier.padding(top = 10.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.formiga),
                        contentDescription = "",
                        modifier = Modifier
                            .size(70.dp)
                            .padding(top = 25.dp, start = 25.dp)
                    )

                    //Textos
                    Column() {

                        Text(
                            text = "Realizado pela ONG",
                            modifier = Modifier.padding(top = 22.dp, start = 4.dp),
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = ongState,
                            modifier = Modifier.padding(start = 4.dp),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {

                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 130.dp, top = 22.dp)
                                .size(25.dp),
                            tint = Color.LightGray
                        )
                    }
                }

            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 100.dp)
            ) {

                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "",
                    tint = Color.White
                )

            }
            Text(
                text = beginDateState,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 11.sp
            )
        }

        //MAIN
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = titleState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                color = Color(79, 121, 254),
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold

            )
            Spacer(modifier = Modifier.padding(16.dp))

            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp)
                    .fillMaxSize(1f)
            ) {
                Text(
                    text = "Sobre o projeto:",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Text(
                    text = descriptionState,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(end = 50.dp),
                    color = Color.Black,
                )
                Log.i("ds3m", descriptionState.toString())
                Text(
                    text = "Detalhes",
                    modifier = Modifier.padding(top = 52.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.padding(8.dp))

                Row() {
                    Icon(
                        imageVector = Icons.Default.Alarm,
                        contentDescription = "",
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    Text(
                        text = "x horas de n coisas",
                        modifier = Modifier.padding(top = 2.dp, start = 10.dp)
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Default.Devices,
                        contentDescription = "",
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    Text(
                        text = "Pode ser feito online",
                        modifier = Modifier.padding(top = 2.dp, start = 10.dp)
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Default.PinDrop,
                        contentDescription = "",
                        modifier = Modifier.padding(bottom = 30.dp)
                    )
                    Text(
                        text = addressState,
                        modifier = Modifier.padding(top = 2.dp, start = 10.dp)
                    )
                }

                Text(
                    text = "Albúm",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    cardAlbum()
                    cardCategoria()
                }

                //COMO CONTRIBUIR
                Text(
                    text = "Como contribuir",
                    modifier = Modifier.padding(top = 36.dp, bottom = 24.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Text(
                    text = howToContributeState,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = "Pré-requisitos:",
                    modifier = Modifier.padding(start = 12.dp, top = 10.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Text(
                    text = prerequisitesState,
                    modifier = Modifier.padding(start = 12.dp, top = 3.dp),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.SemiBold
                )
                Card(
                    shape = RoundedCornerShape(50.dp)
                ) {
                    LinearProgressIndicator(
                        progress = 0.3f,
                        modifier = Modifier.size(295.dp, 13.dp),
                        color = Color(79, 121, 254),
                        backgroundColor = Color(217, 217, 217)
                    )
                }
                Text(
                    text = "X vagas Disponíveis",
                    modifier = Modifier.padding(start = 185.dp, top = 5.dp),
                    fontSize = 12.sp
                )
                Button(
                    onClick = { context.startActivity(Intent(context, CreateCampanha::class.java)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp),
                    colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
                ) {
                    Text(
                        text = "INSCREVER-SE",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.hearticon),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 20.dp),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    }
}


//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)

//@Composable
//fun AboutCampanhaPreview() {
//   AboutCampanha()
//}
