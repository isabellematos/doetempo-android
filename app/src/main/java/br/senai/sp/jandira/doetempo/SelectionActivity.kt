@file:JvmName("SelectionActivityKt")

package br.senai.sp.jandira.doetempo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme

class SelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    choose()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun choose() {

    var loginEmailButton by remember {
        mutableStateOf(false)
    }

    var loginGoogleButton by remember {
        mutableStateOf(false)
    }

    var haveAccountButton by remember {
        mutableStateOf(false)
    }

    var enterHereButton by remember {
        mutableStateOf(false)
    }

    //Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.firstpagebackground),
                contentScale = ContentScale.Crop
            ),
        verticalArrangement = Arrangement.Top
    ) {

        //HEADER
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                painter = painterResource(id = R.drawable.doetempologo),
                contentDescription = "logo",
                modifier = Modifier.size(250.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 220.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            Button(
                onClick = {context.startActivity(Intent(context, FirstPageActivity::class.java))},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 38.dp, end = 38.dp)
                    .size(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 254, 199))
            )
            {
                Text(
                    text = stringResource(id = R.string.cadastroEmail),
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.padding(12.dp))

            Text(
                text = stringResource(id = R.string.haveAccount),
                color = Color.Black,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.padding(6.dp))

            Button(
                onClick = { context.startActivity(Intent(context, LoginActivity::class.java)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 38.dp, end = 38.dp)
                    .size(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(Color(67, 101, 210))
            )
            {
                Text(
                    text = stringResource(id = R.string.enterHere),
                    fontSize = 18.sp
                )
            }
        }
    }
}