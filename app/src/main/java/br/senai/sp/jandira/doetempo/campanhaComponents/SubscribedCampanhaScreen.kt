package br.senai.sp.jandira.doetempo.campanhaComponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun subscribedCampanhaScreen(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    Dialog(
        onDismissRequest = { onDismiss },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .size(width = 150.dp, height = 200.dp),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color(217, 217, 217)
        ) {
            Column() {
                Text(
                    text = "Parabéns, você agora esta inscrito nessa campanha!",
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
                        onClick = { onDismiss() },
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