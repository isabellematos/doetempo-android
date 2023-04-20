package br.senai.sp.jandira.doetempo.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.doetempo.*
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.campanha.CampanhaCall
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import br.senai.sp.jandira.doetempo.services.causas.CausesCall
import buscarCep
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun bottom(
    viewModel: CreateCampanhaViewModel = viewModel()
) {

    var comoContribuirState by remember {
        mutableStateOf("")
    }
    var comoContribuirIsError by remember {
        mutableStateOf(false)
    }
    var preReqsState by remember {
        mutableStateOf("")
    }
    var preReqsIsError by remember {
        mutableStateOf(false)
    }

    var nomeCampanhaState by remember {
        mutableStateOf("")
    }

    var nomeCampanhaIsError by remember {
        mutableStateOf(false)
    }

    var sobreCampanhaState by remember {
        mutableStateOf("")
    }

    var sobreCampanhaIsError by remember {
        mutableStateOf(false)
    }

    var beginDate = rememberSheetState()

    var endDate = rememberSheetState()


    var beginDateState by remember {
        mutableStateOf("")
    }

    var endDateState by remember {
        mutableStateOf("")
    }

    var homeOfficeState by remember {
        mutableStateOf(false)
    }

    var cepState by remember {
        mutableStateOf("")
    }
    var cepIsError by remember {
        mutableStateOf(false)
    }
    var ruaState by remember {
        mutableStateOf("")
    }
    var ruaIsError by remember {
        mutableStateOf(false)
    }
    var numeroState by remember {
        mutableStateOf("")
    }
    var numeroIsError by remember {
        mutableStateOf(false)
    }
    var complementoState by remember {
        mutableStateOf("")
    }
    var complementoIsError by remember {
        mutableStateOf(false)
    }

    var estadoState by remember {
        mutableStateOf("")
    }

    var estadoIsError by remember {
        mutableStateOf(false)
    }

    var cidadeState by remember {
        mutableStateOf("")
    }
    var cidadeIsError by remember {
        mutableStateOf(false)
    }
    var bairroState by remember {
        mutableStateOf("")
    }
    var bairroIsError by remember {
        mutableStateOf(false)
    }

//    val storage = FirebaseStorage.getInstance()
//
//    // Create a storage reference from our app
//    val storageRef = storage.reference.child("documents/document/").child("image%")

    val state = viewModel.state
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )
    SideEffect {
        permissionState.launchPermissionRequest()
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents(),
            onResult = {
                viewModel.updateSelectedImageList(
                    listOfImages = it
                )
            }
        )


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 55.dp, top = 30.dp, end = 55.dp),
        backgroundColor = Color(246,246,246)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.paste),
            modifier = Modifier
                .padding(top = 60.dp)
                .size(100.dp),
            contentDescription = ""
        )

        Column (
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment =  Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenWidth * 0.5f)
            ){

                val context = LocalContext.current

                fun getBitmapFromUri(uri: Uri): Bitmap? {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()
                    return bitmap
                }

                if (state.listOfSelectedImages.isNotEmpty()){
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ){
                        itemsIndexed(state.listOfSelectedImages){index: Int, item: Uri ->
                            val image = item
                            val bitmap = getBitmapFromUri(image)
                            val baos = ByteArrayOutputStream()
                            if (bitmap != null) {
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                            }
                            val data = baos.toByteArray()

//                            storageRef.putBytes(data)
//                                .addOnSuccessListener { taskSnapshot ->
//                                    // A imagem foi enviada com sucesso
//                                }
//                                .addOnFailureListener { exception ->
//                                    // Ocorreu um erro ao enviar a imagem
//                                }
                            Log.i("ds3m", item.toString())
                            ImagePreviewItem(
                                uri = item,
                                height = screenHeight * 0.5f,
                                width = screenWidth * 0.6f,
                                onClick = {
                                    viewModel.onItemRemove(index)
                                }
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                        }
                    }
                }
                if(state.listOfSelectedImages.isNotEmpty()){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                    }

                }
            }
            Button(
                onClick = {


                    if (permissionState.status.isGranted){
                        galleryLauncher.launch("image/*")
                    }else
                        permissionState.launchPermissionRequest()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
            ){
                Text(
                    text = "Upload",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.uploadicon),
                    modifier = Modifier.padding(start = 10.dp),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "───────────────────────",
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp),
            color = Color.LightGray
        )
    }



    //Controla o foco
    val weightFocusRequester = FocusRequester()


    var calendarState = rememberSheetState()

    CalendarDialog(
        state = beginDate,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = false,
            style = CalendarStyle.MONTH
        ), selection = CalendarSelection.Date { dateBegin ->
            Log.d("SelectedDateBegin", "$dateBegin")
            beginDateState = "$dateBegin"
        }
    )

    CalendarDialog(
        state = endDate,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = false,
            style = CalendarStyle.MONTH
        ), selection = CalendarSelection.Date { dateEnd ->
            Log.d("SelectedDateEnd", "$dateEnd")
            endDateState = "$dateEnd"
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {

        OutlinedTextField(
            value = nomeCampanhaState,
            onValueChange = { newName ->
                if (newName.length == 0) {
                    nomeCampanhaIsError = true
                    newName
                } else {
                    nomeCampanhaIsError = false
                }
                nomeCampanhaState = newName
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(weightFocusRequester)
                .padding(start = 16.dp, end = 16.dp),
            label = {
                Text(
                    text = "Nome da campanha",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold

                )
            },
            trailingIcon = {
                if (nomeCampanhaIsError) Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = sobreCampanhaState,
            onValueChange = { newSobre ->
                if (newSobre == "") {
                    sobreCampanhaIsError = true
                    newSobre
                } else {
                    sobreCampanhaIsError = false
                }
                sobreCampanhaState = newSobre
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(weightFocusRequester)
                .padding(start = 16.dp, end = 16.dp)
                .size(200.dp),
            label = {
                Text(
                    text = "Sobre a campanha",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                if (sobreCampanhaIsError) Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = "Local",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 18.dp),
            fontSize = 18.sp,
            color = Color.DarkGray
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //ADICIONAR LOCAL
//            IconButton(
//                onClick = {
//                    viewModel.onAddClick()
//                },
//                modifier = Modifier.padding(start = 6.dp, top = 9.dp)
//
//            ) {
//                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
//            }

//            Text(
//                text = "Adicionar mais locais",
//                modifier = Modifier.padding(top = 9.dp, start = 0.dp)
//            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .size(width = 330.dp, height = 515.dp),
                shape = RoundedCornerShape(10.dp),
                backgroundColor = Color(217, 217, 217)
            ) {
                Column() {

                    OutlinedTextField(
                        value = cepState,
                        onValueChange = { newCep ->
                            if (newCep.length == 0) {
                                cepIsError = true
                                newCep
                            } else {
                                newCep.get(newCep.length - 1)
                                cepIsError = false
                            }
                            if (newCep.length == 8) {
//                        Log.i("ds3m", newCep)
                                buscarCep(newCep) {
                                    cidadeState = it.cidade
                                    ruaState = it.logradouro
                                    estadoState = it.estado
                                    bairroState = it.bairro
                                    cepState = it.cep
                                }.toString()
                            }
                            cepState = newCep
                        },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (cepIsError) Icon(
                                imageVector = Icons.Rounded.Warning,
                                contentDescription = ""
                            )
                        },

                        label = {
                            Text(
                                text = stringResource(id = R.string.cep),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                            )
                        },
                        isError = cepIsError,
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent.copy()
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = estadoState,
                        onValueChange = { newName ->
                            if (newName.length == 0) {
                                ruaIsError = true
                                newName
                            } else {
                                newName.get(newName.length - 1)
                                estadoIsError = false
                            }
                            estadoState = newName
                        },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (estadoIsError) Icon(
                                imageVector = Icons.Rounded.Warning,
                                contentDescription = ""
                            )
                        },

                        label = {
                            Text(
                                text = stringResource(id = R.string.state),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                            )
                        },
                        isError = estadoIsError,
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent.copy()
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = cidadeState,
                        onValueChange = { newName ->
                            if (newName.length == 0) {
                                cidadeIsError = true
                                newName
                            } else {
                                newName.get(newName.length - 1)
                                ruaIsError = false
                            }
                            cidadeState = newName
                        },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (cidadeIsError) Icon(
                                imageVector = Icons.Rounded.Warning,
                                contentDescription = ""
                            )
                        },

                        label = {
                            Text(
                                text = stringResource(id = R.string.city),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                            )
                        },
                        isError = cidadeIsError,
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent.copy()
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = ruaState,
                        onValueChange = { newName ->
                            if (newName.length == 0) {
                                ruaIsError = true
                                newName
                            } else {
                                newName.get(newName.length - 1)
                                ruaIsError = false
                            }
                            ruaState = newName
                        },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (ruaIsError) Icon(
                                imageVector = Icons.Rounded.Warning,
                                contentDescription = ""
                            )
                        },

                        label = {
                            Text(
                                text = stringResource(id = R.string.street),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                            )
                        },
                        isError = ruaIsError,
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent.copy()
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = bairroState,
                        onValueChange = { newName ->
                            if (newName.length == 0) {
                                bairroIsError = true
                                newName
                            } else {
                                newName.get(newName.length - 1)
                                bairroIsError = false
                            }
                            bairroState = newName
                        },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (bairroIsError) Icon(
                                imageVector = Icons.Rounded.Warning,
                                contentDescription = ""
                            )
                        },

                        label = {
                            Text(
                                text = stringResource(id = R.string.bairro),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                            )
                        },
                        isError = bairroIsError,
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent.copy()
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = numeroState,
                        onValueChange = { newName ->
                            if (newName.length == 0) {
                                numeroIsError = true
                                newName
                            } else {
                                newName.get(newName.length - 1)
                                numeroIsError = false
                            }
                            numeroState = newName
                        },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (numeroIsError) Icon(
                                imageVector = Icons.Rounded.Warning,
                                contentDescription = ""
                            )
                        },

                        label = {
                            Text(
                                text = stringResource(id = R.string.number),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                            )
                        },
                        isError = numeroIsError,
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent.copy()
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = complementoState,
                        onValueChange = { newName ->
                            if (newName.length == 0) {
                                complementoIsError = true
                                newName
                            } else {
                                newName.get(newName.length - 1)
                                complementoIsError = false
                            }
                            complementoState = newName
                        },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (complementoIsError) Icon(
                                imageVector = Icons.Rounded.Warning,
                                contentDescription = ""
                            )
                        },

                        label = {
                            Text(
                                text = stringResource(id = R.string.complemento),
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                            )
                        },
                        isError = complementoIsError,
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent.copy()
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

    Column(
        // modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Data de início: ${beginDateState}",
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )

            Text(
                modifier = Modifier.padding(5.dp),
                text = "Data de término: ${endDateState}",
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
        }


        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {


            Button(
                onClick = { beginDate.show() },
                modifier = Modifier.padding(start = 30.dp),
                colors = ButtonDefaults.buttonColors(Color(217, 217, 217)),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Insira data inicial",
                    fontSize = 15.sp,
                )
            }
            Button(
                onClick = { endDate.show() },
                modifier = Modifier.padding(start = 15.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(Color(217, 217, 217)),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Insira data final",
                    fontSize = 15.sp
                )
            }
        }
    }

    Text(
        text = "A ação pode ser feita a distancia?",
        modifier = Modifier.padding(start = 20.dp, top = 15.dp),
        fontWeight = FontWeight.SemiBold
    )

    //RadioButton
    val radioOptions = listOf("Sim", "Não")

    var colorTint by remember {
        mutableStateOf(Color.DarkGray)
    }

    var selectedItem by remember {
        mutableStateOf(radioOptions[0])
    }

    Column(modifier = Modifier.selectableGroup()) {

        radioOptions.forEach { label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (selectedItem == label),
                        onClick = { selectedItem = label },
                        role = Role.RadioButton
                    )
                    .padding(start = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(end = 16.dp),
                    imageVector = if (selectedItem == label)
                        Icons.Outlined.CheckCircle else
                        Icons.Outlined.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
                Text(text = label)

                if (selectedItem == "Sim") {
                    homeOfficeState = true
                } else {
                    homeOfficeState = false
                }
                Log.i("ds3m", homeOfficeState.toString())
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "───────────────────────",
                color = Color.LightGray
            )

        }

    }

    val retrofit = RetrofitFactory.getRetrofit()
    val causesCall = retrofit.create(CausesCall::class.java)
    val call = causesCall.getAll()

    var causesState by remember {
        mutableStateOf(listOf<Cause>())
    }

    call.enqueue(object : Callback<CauseList> {
        override fun onResponse(call: Call<CauseList>, response: Response<CauseList>) {
            causesState = response.body()!!.causes


        }

        override fun onFailure(call: Call<CauseList>, t: Throwable) {
            Log.i("ds3m", t.message.toString())
        }

    })

    val retrofit1 = RetrofitFactory.getRetrofit()
    val causeCall = retrofit1.create(CausesCall::class.java)
    val callCause = causeCall.get()

    var causeName by remember {
        mutableStateOf("")
    }

    callCause.enqueue(object : Callback<Cause> {
        override fun onResponse(call: Call<Cause>, response: Response<Cause>) {
            causeName = response.body()!!.title.toString()

        }

        override fun onFailure(call: Call<Cause>, t: Throwable) {
            Log.i("ds3m", t.message.toString())
        }

    })



    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Adicione tags para sua campanha",
            modifier = Modifier.padding(top = 20.dp, start = 20.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )
        Text(
            text = "(Até três)",
            modifier = Modifier.padding(start = 20.dp, bottom = 24.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )

        Card(
            modifier = Modifier
                .padding(start = 20.dp, bottom = 10.dp),
            shape = RoundedCornerShape(50.dp),
            backgroundColor = Color(165, 218, 230)
        ) {
            Text(
                text = causeName,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(105.dp, 26.dp),
                textAlign = TextAlign.Center

            )
        }

        Card(
            modifier = Modifier
                .padding(start = 20.dp, bottom = 10.dp),
            shape = RoundedCornerShape(50.dp),
            backgroundColor = Color(165, 218, 230)
        ) {
            Text(
                text = "Educação",
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(105.dp, 26.dp),
                textAlign = TextAlign.Center

            )
        }
        dropDownList()


        OutlinedTextField(
            value = comoContribuirState,
            onValueChange = { newComoContribuir ->
                if (newComoContribuir == "") {
                    comoContribuirIsError = true
                    newComoContribuir
                } else {
                    comoContribuirIsError = false
                }
                comoContribuirState = newComoContribuir
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(weightFocusRequester)
                .padding(start = 16.dp, end = 16.dp)
                .size(200.dp),
            label = {
                Text(
                    text = "Como contribuir",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                if (comoContribuirIsError) Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = preReqsState,
            onValueChange = { newReqs ->
                if (newReqs.length == 0) {
                    preReqsIsError = true
                    newReqs
                } else {
                    preReqsIsError = false
                }
                preReqsState = newReqs
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(weightFocusRequester)
                .padding(start = 16.dp, end = 16.dp),
            label = {
                Text(
                    text = "Requisitos",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                if (preReqsIsError) Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(10.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 16.dp)
        ) {

            val context = LocalContext.current

            Button(
                onClick = {

                    nomeCampanhaIsError = nomeCampanhaState.length == 0
                    sobreCampanhaIsError = sobreCampanhaState.length == 0
                    comoContribuirIsError = comoContribuirState.length == 0
                    preReqsIsError = preReqsState.length == 0

                    val text = "Todos os campos são necessarios"
                    val duration = Toast.LENGTH_SHORT

                    if (nomeCampanhaIsError || sobreCampanhaIsError || comoContribuirIsError || preReqsIsError) {
                        val toast = Toast.makeText(context, text, duration)
                        toast.show()
                    } else {
                        val contact = Campanha(
                            title = nomeCampanhaState,
                            description = sobreCampanhaState,
                            begin_date = beginDateState,
                            end_date = endDateState,
                            home_office = homeOfficeState,
                            how_to_contribute = comoContribuirState,
                            id_ngo = "0f68b7cd-07ae-46b2-af39-cf5df5f1e0eb",
                            prerequisites = preReqsState,
                            address = Address(
                                number = numeroState,
                                postalCode = cepState,
                                complement = complementoState
                            ),
                            photos = listOf()
                        )

                        Log.i("ds3m", contact.title.toString())

                        val sharedPreferences =
                            context.getSharedPreferences("app_data", Context.MODE_PRIVATE)
                        val token = sharedPreferences.getString("token", "")
                        Log.i("ds3m tokenn", token.toString())

                        if (!token.isNullOrEmpty()) {
                            val retrofit = RetrofitFactory.getRetrofit()
                            val campanhaCall = retrofit.create(CampanhaCall::class.java)

                            val callContactPost = campanhaCall.save("Bearer $token", contact)

                            callContactPost.enqueue(object : Callback<CreatedCampanha> {
                                override fun onResponse(
                                    call: Call<CreatedCampanha>,
                                    response: Response<CreatedCampanha>
                                ) {
                                    Log.i("headers", response.headers().names().toString())
                                    Log.i("ds3m", response.body()!!.toString())
                                    viewModel.onAddClickCampanha()
                                }

                                override fun onFailure(
                                    call: Call<CreatedCampanha>,
                                    t: Throwable
                                ) {
                                    Log.i("ds3m", t.message.toString())
                                }
                            }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .size(width = 130.dp, height = 40.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
            ) {
                Text(
                    text = "PUBLICAR",
                    color = Color.White
                )
            }
            if (viewModel.isDialogShownCampanha) {
                createdCampanhaScreen(
                    onDismiss = {
                        viewModel.onDismissDialogCampanha()
                    },
                    onConfirm = {
                        context.startActivity(Intent(context, HomeActivity::class.java))
                    })
            }
        }
    }
}




