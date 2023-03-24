package br.senai.sp.jandira.doetempo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.CampanhaComponents.cardAlbum
import br.senai.sp.jandira.doetempo.CampanhaComponents.cardCategoria
import br.senai.sp.jandira.doetempo.CampanhaComponents.comoContribuir
import br.senai.sp.jandira.doetempo.CampanhaComponents.datas
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class CampanhaDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val systemUi = rememberSystemUiController()
                    SideEffect {
                        systemUi.setStatusBarColor(color = Color(79, 121, 254), darkIcons = true)
                    }
                    aboutCampanha()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun aboutCampanha() {

    val scrollState = rememberScrollState()

    var colorTint by remember {
        mutableStateOf(Color.White)
    }


    //Content
    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        //Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomEnd = 70.dp, bottomStart = 70.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color(79, 121, 254))
                    .height(150.dp)
            ) {

                //Imagem e texto
                Row(
                    modifier = Modifier.padding(top = 10.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.formiga),
                        contentDescription = "",
                        modifier = Modifier
                            .size(70.dp)
                            .padding(top = 25.dp, start = 25.dp)
                    )

                    //Textos
                    Column() {

                        Text(
                            text = "Realizado pela ONG",
                            modifier = Modifier.padding(top = 22.dp, start = 4.dp),
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Formigas Albinas",
                            modifier = Modifier.padding(start = 4.dp),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {

                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 130.dp, top = 22.dp)
                                .size(25.dp),
                            tint = Color.LightGray
                        )
                    }
                }

            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 100.dp)
            ) {

                Icon(
                    imageVector = Icons.TwoTone.Favorite,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        if (colorTint == Color.White) {
                            colorTint = Color.Red
                        } else if (colorTint == Color.Red) {
                            colorTint = Color.White
                        }
                    },
                    tint = colorTint
                )

                Spacer(modifier = Modifier.padding(16.dp))

                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "",
                    tint = Color.White
                )

            }
            Text(
                text = "Publicado em 27/02/23",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 11.sp
            )
        }

        //MAIN
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Professor(a) de Matematica",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                color = Color(79, 121, 254),
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold

            )
            Spacer(modifier = Modifier.padding(16.dp))

            Column(
                modifier = Modifier.padding(top = 20.dp, start = 20.dp)
            ) {
                Text(
                    text = "Sobre o projeto:",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus vel lacus enim. Donec posuere eros nec leo placerat commodo. Etiam suscipit ante ultrices tortor rutrum gravida." + " Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse blandit, lorem quis auctor volutpat, nisl purus blandit ante, nec fermentum enim magna in nunc." + " Fusce est massa, finibus id condimentum eu, lacinia nec velit.",
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(end = 50.dp)
                )

                Text(
                    text = "Detalhes",
                    modifier = Modifier.padding(top = 52.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.padding(8.dp))

                Row() {
                    Icon(
                        imageVector = Icons.Default.Alarm,
                        contentDescription = "",
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    Text(
                        text = "x horas de n coisas",
                        modifier = Modifier.padding(top = 2.dp, start = 10.dp)
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Default.Devices,
                        contentDescription = "",
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    Text(
                        text = "Pode ser feito online",
                        modifier = Modifier.padding(top = 2.dp, start = 10.dp)
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Default.PinDrop,
                        contentDescription = "",
                        modifier = Modifier.padding(bottom = 30.dp)
                    )
                    Text(
                        text = "Avenidas das Pitas, 154",
                        modifier = Modifier.padding(top = 2.dp, start = 10.dp)
                    )
                }

                Text(
                    text = "Albúm",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    cardAlbum()
                    cardCategoria()
                }

                Column (
                    modifier = Modifier.fillMaxWidth()
                ){
                    // DATAS
                    Text(
                        text = "Datas",
                        modifier = Modifier.padding(top = 36.dp, bottom = 24.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    datas()

                    //COMO CONTRIBUIR
                    Text(
                        text = "Como contribuir",
                        modifier = Modifier.padding(top = 36.dp, bottom = 24.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    comoContribuir()

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(50.dp),
                        colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
                    ) {
                        Text(
                            text = "INSCREVER-SE",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.hearticon),
                            modifier = Modifier
                                .size(40.dp)
                                .padding(start = 20.dp),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    DoetempoTheme{
    }
}
