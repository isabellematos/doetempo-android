package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.Manifest
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import br.senai.sp.jandira.doetempo.CreateCampanhaViewModel
import br.senai.sp.jandira.doetempo.HomeActivity
import br.senai.sp.jandira.doetempo.ImagePreviewItem
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.model.CreatedPost
import br.senai.sp.jandira.doetempo.model.CreatedUser
import br.senai.sp.jandira.doetempo.model.Post
import br.senai.sp.jandira.doetempo.services.post.PostCall
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewPostActivity : ComponentActivity() {
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


    var newPublication by remember {
        mutableStateOf("")
    }
    var newPublicationisError by remember {
        mutableStateOf(false)
    }

    val weightFocusRequester = FocusRequester()


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
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddAPhoto,
                    modifier = Modifier.size(30.dp),
                    contentDescription = ""
                )
            }
        }

        val context = LocalContext.current

        Button(
            onClick = {
               val contact = Post(
                    content = newPublication,
                    photoURL = state.listOfSelectedImages.toString()
                )

//                val callContactPost = PostCall.save(contact)
//
//                callContactPost.enqueue(object : Callback<CreatedPost> {
//                    override fun onResponse(
//                        call: Call<CreatedPost>,
//                        response: Response<CreatedPost>
//                    ) {
//                        Log.i("ds3m", response.body()!!.toString())
//
//                        context.startActivity(Intent(context, FeedScreenActivity::class.java))
//
//                        Toast.makeText(context, "Post feito com sucesso!", Toast.LENGTH_SHORT)
//                            .show()
//
//                    }
//                    override fun onFailure(call: Call<CreatedPost>, t: Throwable) {
//                        Log.i("ds3m", t.message.toString())
//                    }
//                })


                storageRef.getReference("images").child(System.currentTimeMillis().toString())
                    .putFile(state.listOfSelectedImages[0])
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener {
                                Toast.makeText(context, "$it", Toast.LENGTH_SHORT)
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