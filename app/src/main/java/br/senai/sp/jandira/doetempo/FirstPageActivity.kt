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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme


class FirstPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    selection()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun selection() {

    var ongButton by remember {
        mutableStateOf(false)
    }

    var volunteerButton by remember {
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
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {context.startActivity(Intent(context, SelectionActivity::class.java))
                    context.startActivity(Intent(context, CadastroOngActivity::class.java))
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 38.dp, end = 38.dp)
                    .size(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 254, 199))
            )
            {
                Text(
                    text = stringResource(id = R.string.ong),
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.padding(12.dp))
            Button(
                onClick = {context.startActivity(Intent(context, SelectionActivity::class.java))
                    context.startActivity(Intent(context, SelectionActivity::class.java)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 38.dp, end = 38.dp)
                    .size(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 254, 199))
            )
            {
                Text(
                    text = stringResource(id = R.string.volunteer),
                    fontSize = 18.sp
                )
            }
        }
    }
}