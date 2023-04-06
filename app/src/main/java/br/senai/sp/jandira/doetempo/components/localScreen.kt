package br.senai.sp.jandira.doetempo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun localScreen(
    onDimiss: () -> Unit,
    onConfirm: () -> Unit
) {


    var nomeLocal by remember {
        mutableStateOf("")
    }
    var nomeLocalIsError by remember {
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

    val weightFocusRequester = FocusRequester()


    Dialog(
        onDismissRequest = { onDimiss },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .size(width = 350.dp, height = 450.dp),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color(217, 217, 217)
        ) {
            Column() {
                Text(
                    text = "Adicionar local de camapanha",
                    modifier = Modifier.padding(top = 28.dp, start = 46.dp, bottom = 28.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    text = "Nome do local",
                    modifier = Modifier.padding(start = 28.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                OutlinedTextField(
                    value = nomeLocal,
                    onValueChange = { newNameLocal ->
                        if (newNameLocal.length == 0) {
                            nomeLocalIsError = true
                            newNameLocal
                        } else {
                            nomeLocalIsError = false
                        }
                        nomeLocal = newNameLocal
                    },
                    modifier = Modifier
                        .padding(start = 28.dp, end = 28.dp)
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .size(width = 270.dp, height = 30.dp),
                    trailingIcon = {
                        if (nomeLocalIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                    shape = RoundedCornerShape(5.dp)
                )


                Row() {
                    Text(
                        text = "Cep",
                        modifier = Modifier.padding(start = 28.dp, top = 16.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Cep",
                        modifier = Modifier.padding(start = 124.dp, top = 16.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                }

                Row() {
                    OutlinedTextField(
                        value = cep,
                        onValueChange = { newCep ->
                            if (newCep.length == 0) {
                                cepIsError = true
                                newCep
                            } else {
                                cepIsError = false
                            }
                            cep = newCep
                        },
                        modifier = Modifier
                            .padding(start = 28.dp, end = 20.dp)
                            .focusRequester(weightFocusRequester)
                            .size(width = 128.dp, height = 30.dp),
                        shape = RoundedCornerShape(5.dp)
                    )
                    OutlinedTextField(
                        value = cep,
                        onValueChange = { newCep ->
                            if (newCep.length == 0) {
                                cepIsError = true
                                newCep
                            } else {
                                cepIsError = false
                            }
                            cep = newCep
                        },
                        modifier = Modifier
                            .padding(end = 28.dp)
                            .focusRequester(weightFocusRequester)
                            .size(width = 128.dp, height = 30.dp),
                        shape = RoundedCornerShape(5.dp)
                    )
                }

                ///////////////////////////

                Row() {
                    Text(
                        text = "Número",
                        modifier = Modifier.padding(start = 28.dp, top = 16.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Rua",
                        modifier = Modifier.padding(start = 41.dp, top = 16.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "UF",
                        modifier = Modifier.padding(start = 95.dp, top = 16.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = numero,
                        onValueChange = { newNumero ->
                            if (newNumero.length == 0) {
                                numeroIsError = true
                                newNumero
                            } else {
                                cepIsError = false
                            }
                            numero = newNumero
                        },
                        modifier = Modifier
                            .padding(start = 28.dp, end = 26.dp)
                            .focusRequester(weightFocusRequester)
                            .size(width = 65.dp, height = 30.dp),
                        shape = RoundedCornerShape(5.dp)
                    )
                    OutlinedTextField(
                        value = rua,
                        onValueChange = { newCep ->
                            if (newCep.length == 0) {
                                cepIsError = true
                                newCep
                            } else {
                                cepIsError = false
                            }
                            cep = newCep
                        },
                        modifier = Modifier
                            .padding(end = 24.dp)
                            .focusRequester(weightFocusRequester)
                            .size(width = 95.dp, height = 30.dp),
                        shape = RoundedCornerShape(5.dp)
                    )
                    OutlinedTextField(
                        value = cep,
                        onValueChange = { newCep ->
                            if (newCep.length == 0) {
                                cepIsError = true
                                newCep
                            } else {
                                cepIsError = false
                            }
                            cep = newCep
                        },
                        modifier = Modifier
                            .padding(end = 28.dp)
                            .focusRequester(weightFocusRequester)
                            .size(width = 65.dp, height = 30.dp),
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 70.dp)
                ) {
                    Button(
                        onClick = { onDimiss() },
                        modifier = Modifier.padding(start = 50.dp),
                        colors = ButtonDefaults.buttonColors(Color(243, 112, 112))
                    ) {
                        Text(text = "Cancelar")
                    }
                    Button(
                        onClick = { onConfirm() },
                        modifier = Modifier.padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(Color(71, 230, 234))
                    ) {
                        Text(text = "Salvar")
                    }
                }


            }

        }
    }
}