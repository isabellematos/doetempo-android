package br.senai.sp.jandira.doetempo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import br.senai.sp.jandira.doetempo.campanhaComponents.subscribedCampanhaScreen
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.campanha.CampanhaCall
import br.senai.sp.jandira.doetempo.services.cep.RetrofitApiViaCep
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CampanhaDetailsActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var address by remember {
                mutableStateOf(Cep())
            }

            var campaignDetailsState by remember {
                mutableStateOf(Campanha())
            }

            var idState by remember {
                mutableStateOf("")
            }

            var addressState by remember {
                mutableStateOf("")
            }

            val context = LocalContext.current
            var intent = (context as CampanhaDetailsActivity).intent
            idState = intent.getStringExtra("id").toString()

            val scope = rememberCoroutineScope()
            val datastore = DataStoreAppData(context = context)

            scope.launch {
                if (idState != null) {
                    datastore.saveIdCampanha(idState)
                }
            }

            Log.i("datastore", datastore.getIdCampanha.collectAsState(initial = "").value.toString())

            if (idState != "") {

                val retrofit = RetrofitFactory.getRetrofit()
                val campanhaCall = retrofit.create(CampanhaCall::class.java)
                val call = campanhaCall.getById(idState)
                call.enqueue(object : Callback<Campanha> {

                    override fun onResponse(
                        call: Call<Campanha>,
                        response: Response<Campanha>
                    ) {
                        Log.i("campanha_id", response.body().toString())
                        idState = response.body()!!.id.toString()
                        addressState = response.body()!!.campaignAddress?.postalCode.toString()
                    }

                    override fun onFailure(call: Call<Campanha>, t: Throwable) {
                        Log.i("ds3m", t.message.toString())
                    }

                })

            } else {
                Log.i("ds3m", "erro: id vazio")

            }


            val dataStore = DataStoreAppData(this)
            val typeUser = dataStore.getTypeUser.collectAsState(initial = "").value.toString()

            if (campaignDetailsState.campaignAddress != null) {
                val cep = campaignDetailsState.campaignAddress?.postalCode.toString()
                val callViaCep =
                    RetrofitApiViaCep.getViaCepService().getAddress(cep)
                Log.i("cepcep", cep)

                callViaCep.enqueue(object : Callback<Cep> {
                    override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
                        Log.i("viacep", response.body().toString())
                        if (response.isSuccessful) {
                            address = response.body()!!
                        }
                    }

                    override fun onFailure(call: Call<Cep>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }

                DoetempoTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                    ) {
                        val systemUi = rememberSystemUiController()
                        SideEffect {
                            systemUi.setStatusBarColor(
                                color = Color(79, 121, 254),
                                darkIcons = true
                            )
                        }
                        AboutCampanha(
                            campanha = Campanha(),
                            address = Cep(
                                cep = "",
                                logradouro = "",
                                complemento = "",
                                bairro = "",
                                cidade = "",
                                estado = ""
                            ),
                            typeUser,
                        )
                    }
                }
            }
        }

    //@Preview(showBackground = true, showSystemUi = true)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    @Composable
    fun AboutCampanha(campanha: Campanha, address: Cep, typeUser: String) {

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

        var idState by remember {
            mutableStateOf("")
        }

        var descriptionState by remember {
            mutableStateOf("")
        }

        var beginDateState by remember {
            mutableStateOf("")
        }

        var photoURlNGOState by remember {
            mutableStateOf("")
        }

        var addressState by remember {
            mutableStateOf("")
        }

        var photoURLCampanhaState by remember {
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

        var formattedDateTime by remember {
            mutableStateOf("")
        }


        var prerequisitesState by remember {
            mutableStateOf("")
        }

        val context = LocalContext.current
        var intent = (context as CampanhaDetailsActivity).intent
        idState = intent.getStringExtra("id").toString()

        val scope = rememberCoroutineScope()
        val datastore = DataStoreAppData(context = context)

        scope.launch {
            if (idState != null) {
                datastore.saveIdCampanha(idState)
            }
        }

        Log.i("datastore", datastore.getIdCampanha.collectAsState(initial = "").value.toString())

        if (idState != "") {
            val call = campanhaCall.getById(idState)
            call.enqueue(object : Callback<Campanha> {

                override fun onResponse(
                    call: Call<Campanha>,
                    response: Response<Campanha>
                ) {
                    Log.i("campanha_id", response.body().toString())
                    titleState = response.body()!!.title.toString()
                    descriptionState = response.body()!!.description.toString()
                    beginDateState = response.body()!!.begin_date.toString()
                    endDateState = response.body()!!.end_date.toString()
                    homeOfficeState = response.body()!!.home_office!!
                    howToContributeState = response.body()!!.how_to_contribute.toString()
                    prerequisitesState = response.body()!!.prerequisites.toString()
                    ongState = response.body()!!.ngo?.name.toString()
                    idOngState = response.body()!!.ngo?.id.toString()
                    photoURlNGOState = response.body()!!.ngo?.photo_url.toString()
                    photoURLCampanhaState = response.body()!!.campaignPhotos?.get(0)?.photoURL.toString()
                    Log.i("imgcampanha", photoURLCampanhaState)
                    idState = response.body()!!.id.toString()
                    addressState = response.body()!!.campaignAddress?.postalCode.toString()



                    val zonedDateTime = ZonedDateTime.parse(beginDateState)
                    val localDate = zonedDateTime.toLocalDateTime().minusHours(3)
                    val formatterPattern = DateTimeFormatter.ofPattern("dd 'de' MMMM 'às' HH:mm", Locale("pt", "BR"))
                     formattedDateTime = localDate.format(formatterPattern)
                }

                override fun onFailure(call: Call<Campanha>, t: Throwable) {
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

                        AsyncImage(
                            model = photoURlNGOState,
                            contentDescription = null,
                            Modifier
                                .height(90.dp)
                                .padding(start = 20.dp, top = 10.dp)
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
                                    .padding(start = 110.dp, top = 22.dp)
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
                    text = formattedDateTime,
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
                        fontSize = 17.sp
                    )
                    Text(
                        text = "Detalhes",
                        modifier = Modifier.padding(top = 52.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.padding(8.dp))

//                    Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
//                        Icon(painter = painterResource(id = R.drawable.clock_icon), contentDescription = "icone ilustrativo", Modifier.size(27.dp))
//                        Spacer(modifier = Modifier.width(10.dp))
//                        if (campanha.end_date != null && campanha.begin_date != null ) {
//                            var expireDate =   Period.between(LocalDate.now() ,convertIsoStringToLocalDate(campanha.end_date.toString()))
//                            if (expireDate.isNegative) {
//                                Text(
//                                    text = "Campanha encerrada!",
//                                    style = MaterialTheme.typography.body1,
//                                    color = Color(0xB2000000)
//                                )
//                            } else {
//                                Text(
//                                    text = "Encerra em ${expireDate.months} mes(es) e ${expireDate.days} dia(s)",
//                                    style = MaterialTheme.typography.body1,
//                                    color = Color(0xB2000000)
//                                )
//                            }
//                             Period.between(convertIsoStringToLocalDate(campanha.begin_date.toString()), convertIsoStringToLocalDate(campanha.end_date.toString()))
//                        }
//                    }

                    var possibleHomeOffice = "Pode ser feito online"
                    var impossibleHomeOffice = "Não pode ser feito online"

                    if (homeOfficeState == true)
                        possibleHomeOffice

                    if (homeOfficeState == false)
                        impossibleHomeOffice


                    Row() {
                        Icon(
                            imageVector = Icons.Default.Devices,
                            contentDescription = "",
                            modifier = Modifier.padding(bottom = 15.dp)
                        )
                        Text(
                            text = "${address.logradouro}, ${campanha.campaignAddress?.number} - ${address.bairro}, ${address.cidade} - ${address.estado}",
                            modifier = Modifier.padding(top = 2.dp, start = 10.dp)
                        )
                    }

                    Log.i("rua", address.logradouro.toString())
                    Row() {
                        Icon(
                            imageVector = Icons.Default.PinDrop,
                            contentDescription = "",
                            modifier = Modifier.padding(bottom = 30.dp)
                        )
                        if (homeOfficeState == true) {
                            possibleHomeOffice
                            Text(
                                text = possibleHomeOffice,
                                modifier = Modifier.padding(top = 2.dp, start = 10.dp)
                            )
                        } else {
                            impossibleHomeOffice
                            Text(
                                text = impossibleHomeOffice,
                                modifier = Modifier.padding(
                                    top = 2.dp,
                                    start = 10.dp
                                )
                            )
                        }
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
                        Column(
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(335.dp, 240.dp)
                                    .padding(bottom = 12.dp),
                            ) {
                                Column(
                                ) {
                                    AsyncImage(
                                        model = photoURLCampanhaState,
                                        contentDescription = null,
                                        Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                        // cardCategoria()
                    }

                    //COMO CONTRIBUIR
                    Text(
                        text = "Como contribuir",
                        modifier = Modifier.padding(top = 36.dp, bottom = 14.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )

                    Text(
                        text = howToContributeState,
                        modifier = Modifier.padding(10.dp),
                        fontSize = 17.sp,
                        textAlign = TextAlign.Justify
                    )
                    Text(
                        text = "Pré-requisitos:",
                        modifier = Modifier.padding(top = 14.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = prerequisitesState,
                        modifier = Modifier.padding(start = 12.dp, top = 20.dp, bottom = 15.dp),
                        fontSize = 15.sp,
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
//                Text(
//                    text = "X vagas Disponíveis",
//                    modifier = Modifier.padding(start = 185.dp, top = 5.dp),
//                    fontSize = 12.sp
//                )

                    OpenSubscribedScreen(
                        viewModel = CreateCampanhaViewModel(),
                        campanha.id.toString(),
                        typeUser

                    )

                }
            }
        }
    }

    fun handleClickButtonSubscribe(
        viewModel: CreateCampanhaViewModel,
        context: Context,
        campaignId: String,
        userId: String
    ) {
        viewModel.onAddClickCampanha()
        Log.i("id_campaign", campaignId)
        Log.i("id_user", userId)
    }

    @Composable
    fun OpenSubscribedScreen(
        viewModel: CreateCampanhaViewModel,
        campanhaId: String,
        typeUser: String
    ) {


        var buttonState by remember {
            mutableStateOf(ButtonState.IDLE)
        }


        val context = LocalContext.current
        val dataStorelocal = DataStoreAppData(context = context)
        val token = dataStorelocal.getToken.collectAsState(initial = "").value.toString()

        val datastore = DataStoreAppData(context)
        val userId = datastore.getIdUser.collectAsState(initial = "").value.toString()

        if (typeUser == "USER") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp), contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        //handleClickButtonSubscribe(viewModel, context, campanhaId, userId = userId)
                        buttonState = ButtonState.LOADING
                        Log.i("token", token)
                        val registerUserInCampaign = RetrofitFactory
                            .retrofitUserServices()
                            .registerUserInCampaign("Bearer $token", campanhaId)

                        registerUserInCampaign.enqueue(object :
                            Callback<RegisterUserInCampaignResponse> {
                            override fun onResponse(
                                call: Call<RegisterUserInCampaignResponse>,
                                response: Response<RegisterUserInCampaignResponse>
                            ) {
                                if (response.isSuccessful) {
                                    Log.i("inscrito", response.body().toString())
                                    Toast.makeText(
                                        context,
                                        response.body()?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    buttonState = ButtonState.DONE
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Algo deu errado! Tente novamente mais tarde.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    buttonState = ButtonState.IDLE
                                }
                            }

                            override fun onFailure(
                                call: Call<RegisterUserInCampaignResponse>,
                                t: Throwable
                            ) {
                                Toast.makeText(
                                    context,
                                    "Não foi possivel fazer isso agora, tente novamente mais tarde!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                buttonState = ButtonState.IDLE
                            }
                        })
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp)
                        .size(30.dp),
                    colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
                ) {
                    when (buttonState) {
                        ButtonState.IDLE -> Text(
                            text = "Inscrever-se",
                            Modifier.padding(6.dp),
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onSurface
                        )
                        ButtonState.LOADING -> CircularProgressIndicator()
                        ButtonState.DONE -> Text(
                            text = "Você ja está inscrito!",
                            Modifier.padding(8.dp),
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    if (viewModel.isDialogShownCampanha) {
                        subscribedCampanhaScreen(
                            onDismiss = {
                                viewModel.onDismissDialogCampanha()
                            },
                            onConfirm = {
                                context.startActivity(Intent(context, HomeActivity::class.java))
                            })
                    }
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
