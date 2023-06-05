package br.senai.sp.jandira.editdatascreen

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.InsertEmoticon
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.LoginActivity
import br.senai.sp.jandira.doetempo.R
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.user.UserCall
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EditData() {

    var editName by remember {
        mutableStateOf("")
    }
    var editNameisError by remember {
        mutableStateOf(false)
    }

    var editEmail by remember {
        mutableStateOf("")
    }
    var editEmailisError by remember {
        mutableStateOf(false)
    }
    var editAdress by remember {
        mutableStateOf("")
    }
    var editAdressIsError by remember {
        mutableStateOf(false)
    }
    var editTel by remember {
        mutableStateOf("")
    }
    var editTelIsError by remember {
        mutableStateOf(false)
    }
    var editAbout by remember {
        mutableStateOf("")
    }
    var editAboutIsError by remember {
        mutableStateOf(false)
    }


    val scrollState = rememberScrollState()

    //Controla o foco
    val weightFocusRequester = FocusRequester()
    //Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(255, 255, 255))
    ) {
        //Main
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Editar Dados"
                )
                Text(
                    text = "Edite seus dados",
                    modifier = Modifier.padding(start = 6.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            ) {
                //NOME COMPLETO
                Text(
                    text = "Nome completo",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editName,
                    onValueChange = { newEditName ->
                        if (newEditName.length == 0) {
                            editNameisError = true
                            newEditName
                        } else {
                            editNameisError = false
                        }
                        editName = newEditName
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    trailingIcon = {
                        if (editNameisError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                )

                Text(
                    text = "Email",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editEmail,
                    onValueChange = { newEditName ->
                        if (newEditName.length == 0) {
                            editEmailisError = true
                            newEditName
                        } else {
                            editEmailisError = false
                        }
                        editEmail = newEditName
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    trailingIcon = {
                        if (editNameisError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                )

                //ENDEREÇO
                Text(
                    text = "Endereço",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editAdress,
                    onValueChange = { newEditAdress ->
                        if (newEditAdress.length == 0) {
                            editAdressIsError = true
                            newEditAdress
                        } else {
                            editNameisError = false
                        }
                        editName = newEditAdress
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    trailingIcon = {
                        if (editAdressIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                )

                //TELEFONE
                Text(
                    text = "Telefone",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editTel,
                    onValueChange = { newEditTel ->
                        if (newEditTel.length == 0) {
                            editTelIsError = true
                            newEditTel
                        } else {
                            editTelIsError = false
                        }
                        editTel = newEditTel
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    trailingIcon = {
                        if (editTelIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                )

                //SOBRE
                Text(
                    text = "Sobre",
                    modifier = Modifier.padding(start = 32.dp),
                    fontWeight = FontWeight.W500
                )
                OutlinedTextField(
                    value = editAbout,
                    onValueChange = { newEditAbout ->
                        if (newEditAbout.length == 0) {
                            editAboutIsError = true
                            newEditAbout
                        } else {
                            editAboutIsError = false
                        }
                        editAbout = newEditAbout
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(weightFocusRequester)
                        .padding(start = 32.dp, end = 32.dp, bottom = 25.dp)
                        .size(width = 100.dp, height = 110.dp)
                        .border(
                            width = 1.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(shape = RoundedCornerShape(10.dp)),
                    trailingIcon = {
                        if (editAboutIsError) Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = ""
                        )
                    },
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mansmiling),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .size(150.dp)
                            .border(
                                2.dp,
                                color = Color(79, 121, 254),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(shape = RoundedCornerShape(8.dp))
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Adcionar Imagem")
                            Text(
                                text = "Carregar foto",
                            modifier = Modifier.padding(bottom = 10.dp, start = 6.dp, top = 3.dp),
                            fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Excluir imagem")
                            Text(
                                text = "Excluir foto",
                                modifier = Modifier.padding(bottom = 10.dp, start = 6.dp, top = 3.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Outlined.InsertEmoticon, contentDescription = "Excluir imagem")
                            Text(
                                text = "Escolher Icone",
                                modifier = Modifier.padding(bottom = 10.dp, start = 6.dp, top = 3.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                val contact = UpdateUser(
                                    name = editName,
                                    email = editEmail,
                                    password = passwordState,
                                    birthdate =
                                    cpf = cpfState,
                                    address = Address(
                                        number = numberState,
                                        postalCode = editAdress,
                                        complement = null
                                    ),
                                    gender = genderState,
                                    type = Type(),
                                    description = editAbout
                                )


                                val retrofit = RetrofitFactory.getRetrofit()
                                val userCall = retrofit.create(UserCall::class.java)
                                val callContactPost = userCall.update(contact)

                                callContactPost.enqueue(object : Callback<UpdateUser> {
                                    override fun onResponse(
                                        call: Call<UpdateUser>,
                                        response: Response<UpdateUser>
                                    ) {
                                        Log.i("ds3m", response.body()!!.toString())

                                        context.startActivity(Intent(context, LoginActivity::class.java))

                                    }
                                    override fun onFailure(call: Call<UpdateUser>, t: Throwable) {
                                        Log.i("ds3m", t.message.toString())
                                    }
                                })
                            },
                            modifier = Modifier
                                .size(width = 280.dp, height = 60.dp),
                            shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.buttonColors(Color(79, 121, 254))
                        ) {
                            Text(
                                text = "Salvar",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}