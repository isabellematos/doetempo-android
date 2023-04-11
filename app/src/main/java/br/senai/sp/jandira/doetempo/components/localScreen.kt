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
import buscarCep

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

    var stateState by remember {
        mutableStateOf("")
    }

    var bairroState by remember {
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
                    text = "Adicionar local de campanha",
                    modifier = Modifier.padding(top = 28.dp, start = 46.dp, bottom = 28.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    text = "Bairro",
                    modifier = Modifier.padding(start = 28.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                OutlinedTextField(
                    value = bairroState,
                    onValueChange = { newBairro ->
                        if (newBairro.length == 0) {
                            nomeLocalIsError = true
                            newBairro
                        } else {
                            nomeLocalIsError = false
                        }
                        bairroState = newBairro
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

                    OutlinedTextField(
                            value = stateState,
                    onValueChange = { newState ->
                        if (newState.length == 0) {
                            cepIsError = true
                            newState
                        } else {
                            cepIsError = false
                        }
                        stateState = newState
                    },
                    modifier = Modifier
                        .padding(end = 28.dp)
                        .focusRequester(weightFocusRequester)
                        .size(width = 128.dp, height = 30.dp),
                    shape = RoundedCornerShape(5.dp)
                    )
                    Text(
                    text = "UF",
                    modifier = Modifier.padding(start = 95.dp, top = 16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray)
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
                        onValueChange = { newRua ->
                            if (newRua.length == 0) {
                                cepIsError = true
                                newRua
                            } else {
                                cepIsError = false
                            }
                            rua = newRua
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

                            if (newCep.length == 8) {
//                        Log.i("ds3m", newCep)
                                buscarCep(newCep) {
                                    nomeLocal = it.cidade
                                    rua = it.logradouro
                                    stateState = it.estado
                                    bairroState = it.bairro
                                    cep = it.cep
                                }.toString()
                            }
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