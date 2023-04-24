package br.senai.sp.jandira.doetempo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted

@Preview(showSystemUi = true, showBackground = true)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun  CreateCampanhaScreen(viewModel: CreateCampanhaViewModel = viewModel()) {
    val state = viewModel.state
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val permissionState = rememberPermissionState(
        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
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
            if (state.listOfSelectedImages.isNotEmpty()){
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ){
                    itemsIndexed(state.listOfSelectedImages){index: Int, item: Uri ->
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


}