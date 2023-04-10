package br.senai.sp.jandira.doetempo.components

import android.sax.RootElement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun cardLocal() {

Column(
    modifier = Modifier.padding(bottom = 14.dp)
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(90.dp)
            .padding(start = 16.dp, top = 10.dp, end = 160.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(246,246,246)
    ) {
        Column() {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Outlined.Home,
                modifier = Modifier.padding(6.dp),
                contentDescription = ""
            )
            Text(
                text = "{Endereço Resgistado}",
                modifier = Modifier.padding(top = 8.dp)
            )

        }
            Text(
                text = "Av. Paulista, 19, Bloco 3",
                modifier = Modifier.padding(start = 12.dp, bottom = 3.dp),
                fontSize = 12.sp
            )
            Text(
                text = "São Paulo, Sp",
                modifier = Modifier.padding(start = 12.dp),
                fontSize = 12.sp
            )
        }
    }
}
}