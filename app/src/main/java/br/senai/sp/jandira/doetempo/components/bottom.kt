package br.senai.sp.jandira.doetempo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import br.senai.sp.jandira.doetempo.components.dropDownList
import br.senai.sp.jandira.doetempo.model.Campanha

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun bottom() {

    var comoContribuirState by remember {
        mutableStateOf("")
    }
    var comoContribuirIsError by remember {
        mutableStateOf(false)
    }
    var preReqsState by remember {
        mutableStateOf("")
    }
    var preReqsIsError by remember {
        mutableStateOf(false)
    }
    val weightFocusRequester = FocusRequester()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Adicione tags para sua campanha",
            modifier = Modifier.padding(top = 20.dp, start = 20.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )
        Text(
            text = "(Até três)",
            modifier = Modifier.padding(start = 20.dp, bottom = 24.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )

        Card(
            modifier = Modifier
                .padding(start = 20.dp, bottom = 10.dp),
            shape = RoundedCornerShape(50.dp),
            backgroundColor = Color(165, 218, 230)
        ) {
            Text(
                text = "Educação",
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(105.dp, 26.dp),
                textAlign = TextAlign.Center

            )
        }

        Card(
            modifier = Modifier
                .padding(start = 20.dp, bottom = 10.dp),
            shape = RoundedCornerShape(50.dp),
            backgroundColor = Color(165, 218, 230)
        ) {
            Text(
                text = "Educação",
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(105.dp, 26.dp),
                textAlign = TextAlign.Center

            )
        }
        dropDownList()


        ////////////////////////


        OutlinedTextField(
            value = comoContribuirState,
            onValueChange = { newComoContribuir ->
                if (newComoContribuir == "") {
                    comoContribuirIsError = true
                    newComoContribuir
                } else {
                    comoContribuirIsError = false
                }
                comoContribuirState = newComoContribuir
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(weightFocusRequester)
                .padding(start = 16.dp, end = 16.dp)
                .size(200.dp),
            label = {
                Text(
                    text = "Como contribuir",
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                if (comoContribuirIsError) Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
            },
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = preReqsState,
            onValueChange = { newReqs ->
                if (newReqs.length == 0) {
                    preReqsIsError = true
                    newReqs
                } else {
                   preReqsIsError = false
                }
                preReqsState = newReqs
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(weightFocusRequester)
                .padding(start = 16.dp, end = 16.dp),
            label = {
                Text(
                    text = "Nome da camapanha",
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                if (preReqsIsError) Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
            },
            shape = RoundedCornerShape(10.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 16.dp)
        ) {


            Button(
                onClick = {
                        val contact = Campanha(
                            title = ,
                            description =  ,
                            begin_date = ,
                            end_date = ,
                            home_office = ,
                            how_to_contribute = comoContribuirState,
                            prerequisites = preReqsState ,

                        )




                },
                modifier = Modifier
                    .size(width = 130.dp, height = 40.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
            ) {
                Text(
                    text = "PUBLICAR",
                    color = Color.White
                )
            }
        }
    }
}

