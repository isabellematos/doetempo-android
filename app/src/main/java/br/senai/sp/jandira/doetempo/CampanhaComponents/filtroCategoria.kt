package br.senai.sp.jandira.doetempo.CampanhaComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.doetempo.ui.theme.Shapes

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun cardCategoria() {


    var colorCard by remember {
        mutableStateOf(Color.White)
    }

    val filtroCard by remember {
        mutableStateOf("")
    }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .size(106.dp, 26.dp)
                    .clickable {},
                shape = RoundedCornerShape(50.dp),
                backgroundColor = colorCard
            ) {
                Text(
                    text = "Filtro",
                    modifier = Modifier.padding(top = 3.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.padding(12.dp))

            Card(
                modifier = Modifier
                    .size(106.dp,26.dp),
                shape = RoundedCornerShape(50.dp),
                backgroundColor = colorCard
            ) {
                Text(
                    text = "Filtro",
                    modifier = Modifier.padding(top = 3.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.padding(12.dp))

            Card(
                modifier = Modifier
                    .size(106.dp,26.dp),
                shape = RoundedCornerShape(50.dp),
                backgroundColor = colorCard
            ) {
                Text(
                    text = "Filtro",
                    modifier = Modifier.padding(top = 3.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )
            }
        }

    }
