package br.senai.sp.jandira.doetempo.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.model.Campanha
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import br.senai.sp.jandira.doetempo.CreateCampanhaScreen
import br.senai.sp.jandira.doetempo.model.CreatedCampanha
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.campanha.CampanhaCall
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import br.senai.sp.jandira.doetempo.CreateCampanhaViewModel
import br.senai.sp.jandira.doetempo.HomeActivity
import br.senai.sp.jandira.doetempo.model.Address
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun bottom(
    viewModel: CreateCampanhaViewModel
) {

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


    var nomeCampanhaState by remember {
        mutableStateOf("")
    }

    var sobreCampanhaState by remember {
        mutableStateOf("")
    }

    var beginDate = rememberSheetState()

    var endDate = rememberSheetState()


    var beginDateState by remember {
        mutableStateOf("")
    }

    var endDateState by remember {
        mutableStateOf("")
    }

    var contribuirCampanha by remember {
        mutableStateOf("")
    }

    var requisitosCampanha by remember {
        mutableStateOf("")
    }

    var homeOfficeState by remember {
        mutableStateOf(false)
    }

    var addressState by remember {
        mutableStateOf("")
    }


    var allError by remember {
        mutableStateOf(false)
    }

    //Controla o foco
    val weightFocusRequester = FocusRequester()


    var calendarState = rememberSheetState()

    CalendarDialog(
        state = beginDate,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = false,
            style = CalendarStyle.MONTH
        ), selection = CalendarSelection.Date { dateBegin ->
            Log.d("SelectedDateBegin", "$dateBegin")
            beginDateState = "$dateBegin"
        }
    )

    CalendarDialog(
        state = endDate,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = false,
            style = CalendarStyle.MONTH
        ), selection = CalendarSelection.Date { dateEnd ->
            Log.d("SelectedDateEnd", "$dateEnd")
            endDateState = "$dateEnd"
        }
    )



    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {

        OutlinedTextField(
            value = nomeCampanhaState,
            onValueChange = { newName ->
                if (newName.length == 0) {
                    allError = true
                    newName
                } else {
                    allError = false
                }
                nomeCampanhaState = newName
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(weightFocusRequester)
                .padding(start = 16.dp, end = 16.dp),
            label = {
                Text(
                    text = "Nome da campanha",
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                if (allError) Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
            },
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = sobreCampanhaState,
            onValueChange = { newSobre ->
                if (newSobre == "") {
                    allError = true
                    newSobre
                } else {
                    allError = false
                }
                sobreCampanhaState = newSobre
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(weightFocusRequester)
                .padding(start = 16.dp, end = 16.dp)
                .size(200.dp),
            label = {
                Text(
                    text = "Sobre a campanha",
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                if (allError) Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
            },
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.padding(18.dp))

        Text(
            text = "Local",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 18.dp),
            fontSize = 18.sp,
            color = Color.DarkGray
        )



        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {


            //BOTAO DE ADCIONAR LOCAL
            IconButton(
                onClick = {
                    viewModel.onAddClick()
                },
                modifier = Modifier.padding(start = 6.dp, top = 9.dp)

            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
            if (viewModel.isDialogShown) {
                localScreen(
                    onDimiss = {
                        viewModel.onDimissiDialog()
                    },
                    onConfirm = {
                        //SALVA AS INFOS
                    }
                )
            }
            Text(
                text = "Adicionar mais locais",
                modifier = Modifier.padding(top = 9.dp, start = 0.dp)
            )
        }

        Text(
            text = "Informações Adicionais",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 18.dp, top = 32.dp, bottom = 16.dp),
            fontSize = 18.sp,
            color = Color.DarkGray
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Data de início: ${beginDateState}",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )

                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Data de término: ${endDateState}",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            }


            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {


                Button(
                    onClick = { beginDate.show() },
                    modifier = Modifier.size(width = 80.dp, height = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color(217, 217, 217)),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Insira",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
                Button(
                    onClick = { endDate.show() },
                    modifier = Modifier.size(width = 80.dp, height = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color(217, 217, 217)),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Insira",
                        fontSize = 16.sp
                    )
                }
            }
        }

        Text(
            text = "A ação pode ser feita a distancia?",
            modifier = Modifier.padding(start = 20.dp, top = 15.dp),
            fontWeight = FontWeight.SemiBold
        )

        //RadioButton
        val radioOptions = listOf("Sim", "Não")

        var colorTint by remember {
            mutableStateOf(Color.DarkGray)
        }

        var selectedItem by remember {
            mutableStateOf(radioOptions[0])
        }

        Column(modifier = Modifier.selectableGroup()) {

            radioOptions.forEach { label ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (selectedItem == label),
                            onClick = { selectedItem = label },
                            role = Role.RadioButton
                        )
                        .padding(start = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 16.dp),
                        imageVector = if (selectedItem == label)
                            Icons.Outlined.CheckCircle else
                            Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = Color.DarkGray
                    )
                    Text(text = label)

                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "───────────────────────",
                    color = Color.LightGray
                )

            }

        }

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
                    if (comoContribuirIsError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
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
                        text = "Requisitos",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                trailingIcon = {
                    if (preReqsIsError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(10.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 16.dp)
            ) {

                val context = LocalContext.current

                Button(
                    onClick = {

                        val contact = Campanha(
                            title = nomeCampanhaState,
                            description = sobreCampanhaState,
                            begin_date = beginDateState,
                            end_date = endDateState,
                            home_office = true,
                            how_to_contribute = comoContribuirState,
                            id_ngo = "0f68b7cd-07ae-46b2-af39-cf5df5f1e0eb",
                            prerequisites = preReqsState,
                            tbl_campaign_address = Address(
                                number = "99",
                                postalCode = "06626420",
                                complement = null
                            )
                        )

                        Log.i("ds3m", contact.title.toString())

                        val sharedPreferences =
                            context.getSharedPreferences("app_data", Context.MODE_PRIVATE)
                        val token = sharedPreferences.getString("token", "")
                        Log.i("ds3m tokenn", token.toString())

                        if (!token.isNullOrEmpty()) {
                            val retrofit = RetrofitFactory.getRetrofit()
                            val campanhaCall = retrofit.create(CampanhaCall::class.java)

                            val callContactPost = campanhaCall.save("Bearer $token", contact)

                            callContactPost.enqueue(object : Callback<CreatedCampanha> {
                                override fun onResponse(
                                    call: Call<CreatedCampanha>,
                                    response: Response<CreatedCampanha>
                                ) {
                                    Log.i("headers", response.headers().names().toString())
                                    Log.i("ds3m", response.body()!!.toString())
                                    viewModel.onAddClick()
                                }

                                override fun onFailure(call: Call<CreatedCampanha>, t: Throwable) {
                                    Log.i("ds3m", t.message.toString())
                                }
                            }
                            )
                        }
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
                if (viewModel.isDialogShown) {
                    createdCampanhaScreen(
                        onDimiss = {
                            viewModel.onDimissiDialog()
                        },
                        onConfirm = {
                            //SALVA AS INFOS
                        }
                    )
                }
            }
        }
    }
}

