package br.senai.sp.jandira.doetempo.campanhaComponents

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.doetempo.*
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
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

    var imageLink by remember {
        mutableStateOf("")
    }

    var filePath by remember {
        mutableStateOf("")
    }

    var nomeCampanhaIsError by remember {
        mutableStateOf(false)
    }

    var sobreCampanhaState by remember {
        mutableStateOf("")
    }

    var imageItState by remember {
        mutableStateOf(listOf(""))
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


    var galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents(),
            onResult = {
                viewModel.updateSelectedImageList(
                    listOfImages = it
                )
            }
        )

    var storageRef: FirebaseStorage

    storageRef = FirebaseStorage.getInstance()


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

    class UploadImageActivity : ComponentActivity() {
//        private lateinit var image: ImageView
//        private var bntBrowse by Delegates.notNull<Boolean>()
//        private var bntUpload by Delegates.notNull<Boolean>()

        private var storageRef = Firebase.storage

//        private lateinit var uri: Uri

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            storageRef = FirebaseStorage.getInstance()

        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 55.dp, top = 30.dp, end = 55.dp)
            .clickable {
                if (permissionState.status.isGranted) {
                    galleryLauncher.launch("image/*")
                } else
                    permissionState.launchPermissionRequest()
            },
        backgroundColor = Color(246, 246, 246)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.paste),
            modifier = Modifier
                .padding(top = 60.dp)
                .size(100.dp),
            contentDescription = ""
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenWidth * 0.5f)
            ) {
                if (state.listOfSelectedImages.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {
                        itemsIndexed(state.listOfSelectedImages) { index: Int, item: Uri ->
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

                            filePath = item.toString()
                        }
                    }
                }
                if (state.listOfSelectedImages.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                    }
                }
            }

            val context = LocalContext.current
            Button(
                onClick = {
                    storageRef.getReference("images")
                        .child(System.currentTimeMillis().toString())
                        .putFile(state.listOfSelectedImages[0])
                        .addOnSuccessListener { task ->
                            task.metadata!!.reference!!.downloadUrl
                                .addOnSuccessListener {
                                    imageItState = listOf(it.toString())
                                    Toast.makeText(context, "Imagem enviada com sucesso!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                .addOnFailureListener { error ->
                                    Toast.makeText(
                                        context,
                                        state.listOfSelectedImages.toString(),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                        }

                    val storageRef =
                        FirebaseStorage.getInstance().reference.child("images/${filePath.toUri().lastPathSegment}")

                    val uploadTask = storageRef.putFile(filePath.toUri())
                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        storageRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            imageLink = task.result.toString()
                            Log.i("imagelink", imageLink)
                        } else {

                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
            ) {
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
                                    cidadeState = it.cidade.toString()
                                    ruaState = it.logradouro.toString()
                                    estadoState = it.estado.toString()
                                    bairroState = it.bairro.toString()
                                    cepState = it.cep.toString()
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

    val context = LocalContext.current

    val datastore = DataStoreAppData(context)

    val scope = rememberCoroutineScope()

    val token = datastore.getToken.collectAsState(initial = "").value.toString()

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

//        Card(
//            modifier = Modifier
//                .padding(start = 20.dp, bottom = 10.dp),
//            shape = RoundedCornerShape(50.dp),
//            backgroundColor = Color(165, 218, 230)
//        ) {
//            Text(
//                text = "Educação",
//                modifier = Modifier
//                    .padding(top = 5.dp)
//                    .size(105.dp, 26.dp),
//                textAlign = TextAlign.Center
//
//            )
//        }

        val retrofit1 = RetrofitFactory.getRetrofit()
        val causeCall = retrofit1.create(CausesCall::class.java)
        val callCause = causeCall.getAll()

        var causesState by remember {
            mutableStateOf(listOf(Cause()))
        }

        // causesState = listOf(Cause(id = "9aeba436-f31a-11ed-ad6b-6045bdf0a5e7"))

        callCause.enqueue(object : Callback<CauseList> {
            override fun onResponse(call: Call<CauseList>, response: Response<CauseList>) {
                causesState = response.body()!!.causes
                Log.i("causa", causesState.toString())
            }

            override fun onFailure(call: Call<CauseList>, t: Throwable) {
                Log.i("ds3m", t.message.toString())
            }

        })

        var idCause by remember {
            mutableStateOf("")
        }

        var selectedOptionText by remember {
            mutableStateOf("")
        }

        Column(
        ) {

            var options = causesState
            var expended by remember {
                mutableStateOf(false)
            }

            ExposedDropdownMenuBox(
                expanded = expended,
                onExpandedChange = {
                    expended = !expended
                },
                modifier = Modifier.padding(start = 20.dp, bottom = 24.dp)
            )
            {
                TextField(
                    value = selectedOptionText,
                    onValueChange = { selectedOptionText = it },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = Color(157, 231, 253)),
                    label = {
                        Text(text = "Tags")
                    },
                    singleLine = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expended) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )

                val filterinOption = options.filter { options!!.contains(it) }

                if (filterinOption.isNotEmpty()) {
                    ExposedDropdownMenu(
                        expanded = expended,
                        onDismissRequest = { expended = false }) {

                        options.forEach { option ->
                            DropdownMenuItem(onClick = {
                                selectedOptionText = option.title.toString()
                                idCause = option.id.toString()
                                expended = false
                            }) {
                                Text(text = option.title.toString())
                            }
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 10.dp),
                shape = RoundedCornerShape(50.dp),
                backgroundColor = Color(165, 218, 230)
            ) {
                Text(
                    text = selectedOptionText,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .size(105.dp, 26.dp),
                    textAlign = TextAlign.Center

                )
            }

        }
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
                        val contact = CreateCampanhaBody(
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
                            photo_url = listOf(imageLink),
                            causes = listOf(
                                Cause(
                                    id = idCause
                                )
                            )
                        )

                       // Log.i("ds3m", photo.toString())


                        //  Log.i("ds3m", contact.title.toString())
                        //  Log.i("ds3m tokenn", token.toString())

                        if (!token.isNullOrEmpty()) {
                            val retrofit = RetrofitFactory.getRetrofit()
                            val campanhaCall = retrofit.create(CampanhaCall::class.java)

                            val callContactPost = campanhaCall.save("Bearer $token", contact)
                            callContactPost.enqueue(object : Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    // Log.i("headers", response.headers().names().toString())
                                    //Log.i("ds3m", response.body()!!.toString())
                                    viewModel.onAddClickCampanha()
                                }

                                override fun onFailure(
                                    call: Call<String>,
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



