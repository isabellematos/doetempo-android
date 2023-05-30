package br.senai.sp.jandira.doetempo.campanhaComponents


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import buscarCep

@Composable
fun localScreen() {

    var estado by remember {
        mutableStateOf("")
    }
    var estadoIsError by remember {
        mutableStateOf(false)
    }
    var cep by remember {
        mutableStateOf("")
    }
    var cepIsError by remember {
        mutableStateOf(false)
    }
    var rua by remember {
        mutableStateOf("")
    }
    var ruaIsError by remember {
        mutableStateOf(false)
    }
    var numero by remember {
        mutableStateOf("")
    }
    var numeroIsError by remember {
        mutableStateOf(false)
    }
    var complemento by remember {
        mutableStateOf("")
    }
    var complementoIsError by remember {
        mutableStateOf(false)
    }
    var cidade by remember {
        mutableStateOf("")
    }
    var cidadeIsError by remember {
        mutableStateOf(false)
    }
    var bairro by remember {
        mutableStateOf("")
    }
    var bairroIsError by remember {
        mutableStateOf(false)
    }

    val weightFocusRequester = FocusRequester()


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
            .size(width = 350.dp, height = 420.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(217, 217, 217)
    ) {
        Column() {
            Text(
                text = "Adicionar local de campanha",
                modifier = Modifier.padding(top = 28.dp, start = 46.dp, bottom = 28.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
            Text(
                text = "Estado",
                modifier = Modifier.padding(start = 28.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
            OutlinedTextField(
                value = estado,
                onValueChange = { newEstado ->
                    if (newEstado.length == 0) {
                        estadoIsError = true
                        newEstado
                    } else {
                        estadoIsError = false
                    }
                    estado = newEstado
                },
                modifier = Modifier
                    .padding(start = 28.dp, end = 28.dp)
                    .fillMaxWidth()
                    .focusRequester(weightFocusRequester)
                    .size(width = 270.dp, height = 30.dp),
                trailingIcon = {
                    if (estadoIsError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(5.dp)
            )

            Row() {
                Text(
                    text = "Cep",
                    modifier = Modifier.padding(start = 31.dp, top = 16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    text = "Rua",
                    modifier = Modifier.padding(start = 135.dp, top = 16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedTextField(
                    value = cep,
                    onValueChange = { newCep ->
                        if (newCep.length == 0) {
                            cepIsError = true
                            newCep
                        } else {
                            cepIsError = false
                        }
                        if (newCep.length == 8) {
//                        Log.i("ds3m", newCep)
                            buscarCep(newCep) {
                                cidade = it.cidade.toString()
                                rua = it.logradouro.toString()
                                estado = it.estado.toString()
                                bairro = it.bairro.toString()
                                cep = it.cep.toString()
                            }.toString()
                        }
                        cep = newCep
                    },
                    modifier = Modifier
                        .padding(start = 28.dp, end = 20.dp)
                        .focusRequester(weightFocusRequester)
                        .size(width = 128.dp, height = 30.dp),
                    trailingIcon = {
                        if (estadoIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    shape = RoundedCornerShape(5.dp)
                )
                OutlinedTextField(
                    value = rua,
                    onValueChange = { newRua ->
                        if (newRua.length == 0) {
                            ruaIsError = true
                            newRua
                        } else {
                            ruaIsError = false
                        }
                        rua = newRua
                    },
                    modifier = Modifier
                        .padding(end = 28.dp)
                        .focusRequester(weightFocusRequester)
                        .size(width = 128.dp, height = 30.dp),
                    trailingIcon = {
                        if (estadoIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    shape = RoundedCornerShape(5.dp)
                )
            }

            ///////////////////////////

            Row() {
                Text(
                    text = "Número",
                    modifier = Modifier.padding(start = 37.dp, top = 16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    text = "Cidade",
                    modifier = Modifier.padding(start = 18.dp, top = 16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    text = "Bairro",
                    modifier = Modifier.padding(start = 63.dp, top = 16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedTextField(
                    value = numero,
                    onValueChange = { newNumero ->
                        if (newNumero.length == 0) {
                            numeroIsError = true
                            newNumero
                        } else {
                            numeroIsError = false
                        }
                        numero = newNumero
                    },
                    modifier = Modifier
                        .padding(start = 28.dp)
                        .focusRequester(weightFocusRequester)
                        .size(width = 55.dp, height = 30.dp),
                    trailingIcon = {
                        if (numeroIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    shape = RoundedCornerShape(5.dp)
                )
                OutlinedTextField(
                    value = cidade,
                    onValueChange = { newCidade ->
                        if (newCidade.length == 0) {
                            cidadeIsError = true
                            newCidade
                        } else {
                            cidadeIsError = false
                        }
                        cidade = newCidade
                    },
                    modifier = Modifier
                        .focusRequester(weightFocusRequester)
                        .size(width = 95.dp, height = 30.dp),
                    trailingIcon = {
                        if (cidadeIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    shape = RoundedCornerShape(5.dp)
                )
                OutlinedTextField(
                    value = bairro,
                    onValueChange = { newBairro ->
                        if (newBairro.length == 0) {
                            bairroIsError = true
                            newBairro
                        } else {
                            bairroIsError = false
                        }
                        bairro = newBairro
                    },
                    modifier = Modifier
                        .padding(end = 28.dp)
                        .focusRequester(weightFocusRequester)
                        .size(width = 95.dp, height = 30.dp),
                    trailingIcon = {
                        if (bairroIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    shape = RoundedCornerShape(5.dp)
                )

            }

            Text(
                text = "Complemento",
                modifier = Modifier.padding(start = 28.dp, top = 16.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
            OutlinedTextField(
                value = complemento,
                onValueChange = { newComplemento ->
                    if (newComplemento.length == 0) {
                        complementoIsError = true
                        newComplemento
                    } else {
                        complementoIsError = false
                    }
                    complemento = newComplemento
                },
                modifier = Modifier
                    .padding(start = 28.dp, end = 28.dp)
                    .fillMaxWidth()
                    .focusRequester(weightFocusRequester)
                    .size(width = 270.dp, height = 30.dp),
                trailingIcon = {
                    if (complementoIsError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(5.dp)
            )
        }
    }
}