package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.doetempo.CreateCampanhaViewModel
import br.senai.sp.jandira.doetempo.ImagePreviewItem
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.post.PostCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewPostActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewPost()
                    //getBitmapFromUri(context = LocalContext.current , uri = Uri.EMPTY)
                }
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NewPost(viewModel: CreateCampanhaViewModel = viewModel()) {

    var newPublication by remember {
        mutableStateOf("")
    }

    var imageLink by remember {
        mutableStateOf("")
    }

    var newPublicationisError by remember {
        mutableStateOf(false)
    }

    var filePath by remember {
        mutableStateOf("")
    }

    val weightFocusRequester = FocusRequester()

    var context = LocalContext.current

    var galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents(),
            onResult = {
                viewModel.updateSelectedImageList(
                    listOfImages = it
                )
            }
        )


    var storageRefUri: FirebaseStorage

    storageRefUri = FirebaseStorage.getInstance()


//    var storage = FirebaseStorage.getInstance()
//    var storageRef = storage.reference

    val state = viewModel.state
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    val datastore = DataStoreAppData(context)
    val token = datastore.getToken.collectAsState(initial = "").value.toString()
    val typeUser = datastore.getTypeUser.collectAsState(initial = "").value.toString()

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )
    SideEffect {
        permissionState.launchPermissionRequest()
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Escreva sobre sua ultima boa ação!",
            modifier = Modifier.padding(15.dp),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
        )
        OutlinedTextField(
            value = newPublication,
            onValueChange = { altPublication ->
                if (altPublication.length == 0) {
                    newPublicationisError = true
                    altPublication
                } else {
                    newPublicationisError = false
                }
                newPublication = altPublication
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .size(290.dp, 120.dp)
                .focusRequester(weightFocusRequester),
            label = {
                Text(
                    text = "Faça uma publicação",
                    fontWeight = FontWeight.Normal
                )
            },
            trailingIcon = {
                if (newPublicationisError) Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(10.dp)
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                                    Log.i("filepath", filePath)
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
                    }
                }
            }

            Button(
                onClick = {
                    storageRefUri.getReference("images")
                        .child(System.currentTimeMillis().toString())
                        .putFile(state.listOfSelectedImages[0])
                        .addOnSuccessListener { task ->
                            task.metadata!!.reference!!.downloadUrl
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "Imagem enviada com sucesso!",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                                .addOnFailureListener { error ->
                                    Toast.makeText(
                                        context,
                                        "erro no carregamento de imagem",
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
                    .size(40.dp)
                    .padding(start = 80.dp, end = 80.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 121, 254)),
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {
                    if (permissionState.status.isGranted) {
                        galleryLauncher.launch("image/*")
                    } else
                        permissionState.launchPermissionRequest()

                }
            )

            {
                Icon(
                    imageVector = Icons.Outlined.AddAPhoto,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(start = 10.dp, top = 10.dp),
                    contentDescription = ""
                )
            }
            //file = state.listOfSelectedImages[0].path?.let { File(it) }.toString()

            var mapImage by remember {
                mutableStateOf(HashMap<String, Any>())
            }

            //Log.i("photopost", filePath.toString())

            Button(
                onClick = {

                    var contact = CreatePost(
                        content = newPublication,
                        typeUser = typeUser,
                        photos = listOf(imageLink)
                    )
                    Log.i("contact", contact.toString())

                    val retrofit = RetrofitFactory.getRetrofit()
                    val postCall = retrofit.create(PostCall::class.java)
                    val callContactPost = postCall.save("Bearer $token", contact)

                    callContactPost.enqueue(object : Callback<CreatedPost> {
                        override fun onResponse(
                            call: Call<CreatedPost>,
                            response: Response<CreatedPost>
                        ) {

                            context.startActivity(
                                Intent(
                                    context,
                                    FeedScreenActivity::class.java
                                )
                            )

                            Toast.makeText(
                                context,
                                "Post feito com sucesso!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        override fun onFailure(call: Call<CreatedPost>, t: Throwable) {
                            //Log.i("ds3m", t.message.toString())
                        }
                    })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .size(50.dp),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
            )

            {
                Text(
                    text = stringResource(id = R.string.send),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

