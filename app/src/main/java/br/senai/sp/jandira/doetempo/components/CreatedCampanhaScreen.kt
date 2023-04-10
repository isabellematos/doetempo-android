package br.senai.sp.jandira.doetempo.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun createdCampanhaScreen(
    onDimiss: () -> Unit,
    onConfirm: () -> Unit
) {

    var nomeLocal by remember {
        mutableStateOf("")
    }

    var nomeLocalIsError by remember {
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
                    text = "Parabéns, sua campanha foi criada com sucesso!",
                    modifier = Modifier.padding(top = 28.dp, start = 46.dp, bottom = 28.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
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