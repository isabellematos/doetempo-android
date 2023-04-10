package br.senai.sp.jandira.doetempo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.exp


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun dropDownList() {

    Column(
    ) {

        val options = listOf("Educacao", "Comunidade", "Tecnologia", "Meio Ambiente")
        var expended by remember {
            mutableStateOf(false)
        }

        var selectedOptionText by remember {
            mutableStateOf("")
        }

        ExposedDropdownMenuBox(
            expanded = expended,
            onExpandedChange = {
                expended = !expended
            },
            modifier = Modifier.padding(start = 20.dp, bottom = 24.dp)
        )
        {
            TextField(
                value = selectedOptionText,
                onValueChange = { selectedOptionText = it },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = Color(157,231,253)),
                label = {
                    Text(text = "Tags")
                },
                singleLine = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expended) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            val filterinOption =
                options.filter { it.contains(selectedOptionText, ignoreCase = true) }

            if(filterinOption.isNotEmpty()){
                ExposedDropdownMenu(expanded = expended, onDismissRequest = { expended = false }) {

                    filterinOption.forEach { selectedText ->
                        DropdownMenuItem(onClick = {
                            selectedOptionText = selectedText
                            expended = false
                        }) {
                            Text(text = selectedText)
                        }
                    }
                }
            }
        }
    }
}