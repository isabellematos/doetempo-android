package br.senai.sp.jandira.doetempo.CampanhaComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun datas() {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Image(
                painter = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.calendarimg),
                modifier = Modifier.size(83.dp, 70.dp),
                contentDescription = ""
            )
            Column() {
                Text(
                    text = "Dia e mes"
                )
                Text(
                    text = "Endereço"
                )
                Text(
                    text = "Horário"
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Image(
                painter = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.calendarimg),
                modifier = Modifier.size(83.dp, 70.dp),
                contentDescription = ""
            )
            Column() {
                Text(
                    text = "Dia e mes"
                )
                Text(
                    text = "Endereço"
                )
                Text(
                    text = "Horário"
                )
            }
        }
    }


}


