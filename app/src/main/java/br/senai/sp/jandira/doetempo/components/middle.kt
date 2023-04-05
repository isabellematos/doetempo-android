package br.senai.sp.jandira.doetempo.components

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDateTime
import java.time.ZoneId
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.core.content.ContextCompat.startActivity
import br.senai.sp.jandira.doetempo.CadastroUser
import br.senai.sp.jandira.doetempo.CreateCampanha
import br.senai.sp.jandira.doetempo.CreateCampanhaViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection


@Composable
fun middleInfos(
    viewModel: CreateCampanhaViewModel
) {

    var nomeCampanhaState by remember {
        mutableStateOf("")
    }

    var sobreCampanhaState by remember {
        mutableStateOf("")
    }

    var contribuirCampanha by remember {
        mutableStateOf("")
    }

    var requisitosCampanha by remember {
        mutableStateOf("")
    }

    var allError by remember {
        mutableStateOf(false)
    }

    //Controla o foco
    val weightFocusRequester = FocusRequester()

    //Calendario
    var calendarState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = false,
            style = CalendarStyle.MONTH
        ), selection = CalendarSelection.Date { date ->
            Log.d("SelectedDate", "$date")
        }
    )

    //relógio
    var clockState = rememberSheetState()
    ClockDialog(
        state = clockState,
        config = ClockConfig(
            is24HourFormat = true
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            Log.d("SelectedDate", "$hours: $minutes")
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
                    text = "Nome da camapanha",
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "Data de início",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )


                Text(
                    text = "Data de término",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {


                Button(
                    onClick = { calendarState.show() },
                    modifier = Modifier.size(width = 80.dp, height = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color(217, 217, 217)),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Teste",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
                Button(
                    onClick = { calendarState.show() },
                    modifier = Modifier.size(width = 80.dp, height = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color(217, 217, 217)),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "$calendarState",
                        fontSize = 16.sp
                    )
                }
            }
            Row() {
                IconButton(
                    onClick = { },
                    modifier = Modifier.padding(start = 6.dp, top = 9.dp)

                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                }
                Text(
                    text = "Adicionar mais datas",
                    modifier = Modifier.padding(top = 23.dp, bottom = 30.dp)
                )
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

    }
}
@Preview
@Composable
fun middleInfosPreview() {
}



