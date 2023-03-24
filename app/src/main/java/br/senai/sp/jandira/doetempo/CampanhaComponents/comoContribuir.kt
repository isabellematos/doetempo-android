package br.senai.sp.jandira.doetempo.CampanhaComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun comoContribuir() {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(height = 240.dp, width = 335.dp)
                .padding(bottom = 12.dp, end = 16.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(0.5.dp, Color.Black)
        ) {
            Column {

                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus vel lacus enim. Donec posuere eros nec leo placerat commodo. Etiam suscipit ante ultrices tortor rutrum gravida." + " Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse blandit, lorem quis auctor volutpat, nisl purus blandit ante, nec fermentum enim magna in nunc." + " Fusce est massa, finibus id condimentum eu, lacinia nec velit",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = "Pré-requisitos:",
                    modifier = Modifier.padding(start = 12.dp, top = 10.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Formação em pedagogia, soft skills",
                    modifier = Modifier.padding(start = 12.dp, top = 3.dp),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Card(
            shape = RoundedCornerShape(50.dp)
        ) {
            LinearProgressIndicator(
                progress = 0.3f,
                modifier = Modifier.size(295.dp, 13.dp),
                color = Color(79, 121, 254),
                backgroundColor = Color(217,217,217)
            )
        }
        Text(
            text = "X vagas Disponíveis",
            modifier = Modifier.padding(start = 185.dp, top = 5.dp),
            fontSize = 12.sp
        )
    }
}