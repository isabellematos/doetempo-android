package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.doetempo.CreateCampanhaViewModel
import br.senai.sp.jandira.doetempo.ImagePreviewItem
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.CreatePost
import br.senai.sp.jandira.doetempo.model.CreatedPost
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.post.PostCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.target.SimpleTarget
//import com.bumptech.glide.request.transition.Transition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.annotation.RequiresApi


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
                }
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewPost(viewModel: CreateCampanhaViewModel = viewModel()) {

    var newPublication by remember {
        mutableStateOf("")
    }

    var bitmapLink by remember {
        mutableStateOf("")
    }

    var filePath by remember {
        mutableStateOf("")
    }

    var imageLink by remember {
        mutableStateOf(listOf(""))
    }


    var newPublicationisError by remember {
        mutableStateOf(false)
    }

    var file by remember {
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

    var storageRef: FirebaseStorage

    storageRef = FirebaseStorage.getInstance()


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
                .size(300.dp, 100.dp)
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenWidth * 0.5f)
            ) {

                val context = LocalContext.current

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

                    storageRef.getReference("images")
                        .child(System.currentTimeMillis().toString())
                        .putFile(state.listOfSelectedImages[0])
                        .addOnSuccessListener { task ->
                            task.metadata!!.reference!!.downloadUrl
                                .addOnSuccessListener {
                                    filePath = listOf(it.path.toString()).toString()
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



//                    var file = state.listOfSelectedImages[0].path?.let { File(it) }
//
//                    Log.i("uriphoto", Uri.fromFile(file).toString())

                    // var imageRef = storageRef.child("images")


                    fun convertUriToFilePath(context: Context, uri: Uri): String {
                        filePath[0].toString()

                        if (true && DocumentsContract.isDocumentUri(context, uri)) {
                            if ("com.android.providers.media.documents" == uri.authority) {
                                val documentId = DocumentsContract.getDocumentId(uri)
                                val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                val selection = MediaStore.Images.Media._ID + "=?"
                                val selectionArgs = arrayOf(documentId.split(":")[1])
                                context.contentResolver.query(contentUri, null, selection, selectionArgs, null)?.use { cursor ->
                                    if (cursor.moveToFirst()) {
                                        val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                                        if (columnIndex != -1) {
                                            filePath = cursor.getString(columnIndex)
                                        }
                                    }
                                }
                            }
                        }

                        return filePath
                    }


// Exemplo de uso
                    val uriString = "content://com.android.providers.media.documents/document/"
                    val uri = Uri.parse(uriString)
                    val filePath = convertUriToFilePath(context, uri)
                    if (filePath != null) {

                        println(filePath)
                    } else {
                        // Não foi possível obter o caminho do arquivo
                        println("Caminho do arquivo não encontrado")
                    }





                   // var uri = Uri.EMPTY

//                    @RequiresApi(Build.VERSION_CODES.P)
//                    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
//                    if (true && DocumentsContract.isDocumentUri(context, uri)) {
//                        if ("com.android.providers.media.documents" == uri.authority) {
//                            val documentId = DocumentsContract.getDocumentId(uri)
//                            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                            val selection = MediaStore.Images.Media._ID + "=?"
//                            val selectionArgs = arrayOf(documentId.split(":")[1])
//                            context.contentResolver.query(contentUri, null, selection, selectionArgs, null)?.use { cursor ->
//                                if (cursor.moveToFirst()) {
//                                    val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
//                                    if (columnIndex != -1) {
//                                        filePath = cursor.getString(columnIndex)
//                                        var source = ImageDecoder.createSource(context.contentResolver, Uri.parse(filePath))
//                                        ImageDecoder.decodeBitmap(source)
//                                    } else {
//                                        null
//                                    }
//                                } else {
//                                    null
//                                }
//                            }
//                        } else {
//                            null
//                        }
//                    } else {
//                        null
//                    }



//                    val imageUrl =
//                        "https://firebasestorage.googleapis.com/v0/b/doe-tempo-50ccb.appspot.com/o/images%${file}" // Substitua pelo seu link de imagem
//
//                    Glide.with(context)
//                        .asBitmap()
//                        .load(imageUrl)
//                        .into(object : SimpleTarget<Bitmap>() {
//                            override fun onResourceReady(
//                                resource: Bitmap,
//                                transition: Transition<in Bitmap>?
//                            ) {
//                                bitmapLink = state.listOfSelectedImages[0].path.toString()
//                            }
//                        })
                }
            )
            {
                Icon(
                    imageVector = Icons.Outlined.AddAPhoto,
                    modifier = Modifier.size(30.dp),
                    contentDescription = ""
                )
            }
        }

        //file = state.listOfSelectedImages[0].path?.let { File(it) }.toString()

        Log.i("photopost", state.listOfSelectedImages.toString())

        Button(
            onClick = {
                val contact = CreatePost(
                    content = newPublication,
                    photos = listOf(filePath),
                    typeUser = typeUser
                )

                val retrofit = RetrofitFactory.getRetrofit()
                val postCall = retrofit.create(PostCall::class.java)
                val callContactPost = postCall.save("Bearer $token", contact)

                callContactPost.enqueue(object : Callback<CreatedPost> {
                    override fun onResponse(
                        call: Call<CreatedPost>,
                        response: Response<CreatedPost>
                    ) {
                        //  Log.i("post", response.body()!!.message)

                        context.startActivity(Intent(context, FeedScreenActivity::class.java))

                        Toast.makeText(context, "Post feito com sucesso!", Toast.LENGTH_SHORT)
                            .show()

                    }

                    override fun onFailure(call: Call<CreatedPost>, t: Throwable) {
                        Log.i("ds3m", t.message.toString())
                    }
                })
//
//                storageRef.getReference("images").child(System.currentTimeMillis().toString())
//                    .putFile(state.listOfSelectedImages[0])
//                    .addOnSuccessListener { task ->
//                        task.metadata!!.reference!!.downloadUrl
//                            .addOnSuccessListener {
//                                Toast.makeText(context, "Imagem enviada com sucesso!", Toast.LENGTH_SHORT)
//                                    .show()
//                            }
//                            .addOnFailureListener { error ->
//                                Toast.makeText(
//                                    context,
//                                    state.listOfSelectedImages.toString(),
//                                    Toast.LENGTH_SHORT
//                                )
//                                    .show()
//                            }
//                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 38.dp, end = 38.dp, bottom = 10.dp)
                .size(40.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(Color(79, 254, 199))
        )
        {
            Text(
                text = stringResource(id = R.string.send),
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}