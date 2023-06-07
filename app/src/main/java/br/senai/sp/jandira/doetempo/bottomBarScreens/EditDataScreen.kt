package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.InsertEmoticon
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.doetempo.*
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.bottomBarScreens.ProfileScreen
import br.senai.sp.jandira.doetempo.bottomBarScreens.ProfileScreenActivity
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.user.UserCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EditDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EditData(user = User())
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditData(viewModel: CreateCampanhaViewModel = viewModel(), user: User) {

    var editName by remember {
        mutableStateOf("")
    }

    var editNameisError by remember {
        mutableStateOf(false)
    }

    var editEmail by remember {
        mutableStateOf("")
    }

    var editEmailisError by remember {
        mutableStateOf(false)
    }

    var imageItState by remember {
        mutableStateOf("")
    }

    var editTel by remember {
        mutableStateOf("")
    }

    var editTelIsError by remember {
        mutableStateOf(false)
    }

    var editAbout by remember {
        mutableStateOf("")
    }

    var editAboutIsError by remember {
        mutableStateOf(false)
    }

    var editPassword by remember {
        mutableStateOf("")
    }

    var editPasswordIsError by remember {
        mutableStateOf(false)
    }

    var imageLink by remember {
        mutableStateOf("")
    }

    var filePath by remember {
        mutableStateOf("")
    }

    var userIdState by remember {
        mutableStateOf("")
    }

    var tokenState by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

   // val dataStore = DataStoreAppData(this)

    var intent = (context as EditDataActivity).intent
    userIdState = intent.getStringExtra("id_user").toString()

    tokenState = intent.getStringExtra("key").toString()


    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)

//    scope.launch {
//        if (token != null && idUser != null ) {
//            datastore.saveToken(token)
//            datastore.saveIdUser(idUser)
//        }
//    }

    Log.i("datastore", datastore.getIdUser.collectAsState(initial = "").value.toString())

    userIdState = datastore.getIdUser.collectAsState(initial = "").value.toString()
    tokenState = datastore.getToken.collectAsState(initial = "").value.toString()


    val retrofit = RetrofitFactory.getRetrofit()
    val userCall = retrofit.create(UserCall::class.java)
    val call = userIdState?.let { userCall.getById("Bearer $tokenState", it) }

    if (call != null) {
        call.enqueue(object : Callback<UserDetailsProfile> {
            override fun onResponse(call: Call<UserDetailsProfile>, response: Response<UserDetailsProfile>) {
                response.body()?.let { Log.i("user", response.body()?.user.toString()) }
                editName = response.body()?.user?.name.toString()
                editEmail = response.body()?.user?.email.toString()
                editPassword = response.body()?.user?.password.toString()
                editTel = response.body()?.user?.userPhone?.phone.toString()
                editAbout = response.body()?.user?.description.toString()
                // = response.body()?.user?.photo_url.toString()
            }

            override fun onFailure(call: Call<UserDetailsProfile>, t: Throwable) {
                Log.i("ds3m", t.message.toString())
            }
        })
    }


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

    val scrollState = rememberScrollState()

    //Controla o foco
    val weightFocusRequester = FocusRequester()
    //Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(255, 255, 255))
    ) {
        //Main
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Editar Dados"
                )
                Text(
                    text = "Edite seus dados",
                    modifier = Modifier.padding(start = 6.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            ) {
                //NOME COMPLETO
                Text(
                    text = "Nome completo",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editName,
                    onValueChange = {editName = it },

 //                           newEditName ->
 //                       if (newEditName.length == 0) {
  //                          editNameisError = true
  //                          newEditName
   //                     } else {
   //                         editNameisError = false
    //                    }
    //                    editName = newEditName
    //                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    trailingIcon = {
                        if (editNameisError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    singleLine = true,
                )

                Text(
                    text = "Email",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editEmail,
                    onValueChange = { newEditName ->
                        if (newEditName.length == 0) {
                            editEmailisError = true
                            newEditName
                        } else {
                            editEmailisError = false
                        }
                        editEmail = newEditName
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    trailingIcon = {
                        if (editNameisError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    singleLine = true,
                )

                //ENDEREÇO
                Text(
                    text = "Senha",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editPassword,
                    onValueChange = { newEditAdress ->
                        if (newEditAdress.length == 0) {
                            editPasswordIsError = true
                            newEditAdress
                        } else {
                            editNameisError = false
                        }
                        editPassword = newEditAdress
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = {
                        if (editPasswordIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    singleLine = true,
                )

                //TELEFONE
                Text(
                    text = "Telefone",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editTel,
                    onValueChange = { newEditTel ->
                        if (newEditTel.length == 0) {
                            editTelIsError = true
                            newEditTel
                        } else {
                            editTelIsError = false
                        }
                        editTel = newEditTel
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    trailingIcon = {
                        if (editTelIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                )

                //SOBRE
                Text(
                    text = "Sobre",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editAbout,
                    onValueChange = { newEditAbout ->
                        if (newEditAbout.length == 0) {
                            editAboutIsError = true
                            newEditAbout
                        } else {
                            editAboutIsError = false
                        }
                        editAbout = newEditAbout
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 220.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    trailingIcon = {
                        if (editAboutIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    singleLine = true,
                )
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.mansmiling),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .padding(top = 10.dp)
//                            .size(150.dp)
//                            .border(
//                                2.dp,
//                                color = Color(79, 121, 254),
//                                shape = RoundedCornerShape(8.dp)
//                            )
//                            .clip(shape = RoundedCornerShape(8.dp))
//                    )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Adcionar Imagem"
                        )
                        Text(
                            text = "Carregar foto",
                            modifier = Modifier.padding(bottom = 10.dp, start = 6.dp, top = 3.dp),
                            fontWeight = FontWeight.SemiBold
                        )
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
                                                    imageItState = listOf(it.toString()).toString()
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Adcionar Imagem")
                            Text(
                                text = "Carregar foto",
                            modifier = Modifier.padding(bottom = 10.dp, start = 6.dp, top = 3.dp),
                            fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Excluir imagem")
                            Text(
                                text = "Excluir foto",
                                modifier = Modifier.padding(bottom = 10.dp, start = 6.dp, top = 3.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Outlined.InsertEmoticon, contentDescription = "Excluir imagem")
                            Text(
                                text = "Escolher Icone",
                                modifier = Modifier.padding(bottom = 10.dp, start = 6.dp, top = 3.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                editName = editEmail
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(
                            onClick = {
                                val contact = UpdateUser(
                                    name = editName ,
                                    email = editEmail,
                                    password = editPassword,
                                    type = Type(),
                                    description = editAbout,
                                    userPhone = UserPhone(
                                        phone = Phone(
                                            number = editTel
                                        )
                                    ),
                                photo_url = imageLink
                                )


                                val retrofit = RetrofitFactory.getRetrofit()
                                val userCall = retrofit.create(UserCall::class.java)
                                val callContactPost =  userIdState?.let { userCall.update("Bearer $tokenState", it, contact) }


                                if (callContactPost != null) {
                                    callContactPost.enqueue(object : Callback<PayloadUserUpdate> {
                                        override fun onResponse(
                                            call: Call<PayloadUserUpdate>,
                                            response: Response<PayloadUserUpdate>
                                        ) {
                                            Log.i("ds3m", response.body()!!.toString())

                                            context.startActivity(Intent(context, ProfileScreenActivity()::class.java))
                                        }

                                        override fun onFailure(
                                            call: Call<PayloadUserUpdate>,
                                            t: Throwable
                                        ) {
                                            Log.i("ds3m", t.message.toString())

                                        }
                                    })
                                }

                            },
                            modifier = Modifier
                                .size(width = 280.dp, height = 60.dp),
                            shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
                        ) {
                            Text(
                                text = "Salvar",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
