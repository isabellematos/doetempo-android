package br.senai.sp.jandira.doetempo.bottomBarScreens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.HomeActivities.cardCampanha
import br.senai.sp.jandira.doetempo.HomeActivity

//import br.senai.sp.jandira.doetempo.HomeActivities.


@Composable
fun CampanhaScreen() {
    //CONTENT

    var userNameState by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    var intent = (context as HomeActivity).intent
    userNameState = intent.getStringExtra("name").toString()

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
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 22.dp, end = 10.dp)
                        .size(24.dp)
                )
            }
        }

        //LazyColumn(content = )
        cardCampanha()
    }
}

