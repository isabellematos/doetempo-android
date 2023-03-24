package br.senai.sp.jandira.doetempo.CampanhaComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun cardAlbum() {

    Column(
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(335.dp, 240.dp)
                .padding(bottom = 12.dp),
            border = BorderStroke(2.dp, color = Color(79, 121, 254))
        ) {
            Column(
            ) {
                Image(
                    painter = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.imgteste),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}