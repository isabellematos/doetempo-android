package br.senai.sp.jandira.doetempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.model.CadastroUser
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme

class CadastroUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CadastroUser()
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Preview
@Composable
fun CadastroUser() {

    var nameState by rememberSaveable() {
        mutableStateOf("")
    }

    var emailState by rememberSaveable() {
        mutableStateOf("")
    }

    var passwordState by rememberSaveable() {
        mutableStateOf("")
    }

    var birthDateState by rememberSaveable() {
        mutableStateOf("")
    }

    var cpfState by rememberSaveable() {
        mutableStateOf("")
    }

    var cepState by rememberSaveable() {
        mutableStateOf("")
    }

    var stateState by rememberSaveable() {
        mutableStateOf("")
    }

    var cityState by rememberSaveable() {
        mutableStateOf("")
    }

    var numberState by rememberSaveable() {
        mutableStateOf("")
    }

    var isNameError by remember {
        mutableStateOf(false)
    }

    var isEmailError by remember {
        mutableStateOf(false)
    }

    var isPasswordError by remember {
        mutableStateOf(false)
    }

    var isBirthDateError by remember {
        mutableStateOf(false)
    }

    var isCpfError by remember {
        mutableStateOf(false)
    }

    var isCepError by remember {
        mutableStateOf(false)
    }

    var isStateError by remember {
        mutableStateOf(false)
    }

    var isCityError by remember {
        mutableStateOf(false)
    }

    var isNumberError by remember {
        mutableStateOf(false)
    }

    class DateTransformation() : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            return dateFilter(text)

        }

        fun dateFilter(text: AnnotatedString): TransformedText {

            val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
            var out = ""
            for (i in trimmed.indices) {
                out += trimmed[i]
                if (i % 2 == 1 && i < 4) out += "/"
            }

            val numberOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 1) return offset
                    if (offset <= 3) return offset + 1
                    if (offset <= 8) return offset + 2
                    return 10
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 5) return offset - 1
                    if (offset <= 10) return offset - 2
                    return 8
                }
            }


            return TransformedText(AnnotatedString(out), numberOffsetTranslator)
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(79, 121, 254)),

        verticalArrangement = Arrangement.Top,

        ) {
        Image(painter = painterResource(id = R.drawable.headerwave), contentDescription = "wave")
        Column(//header
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "icone da aplicacao",
                modifier = Modifier.size(200.dp)
            )
        }
        // Form
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
        ) {
            OutlinedTextField(
                value = nameState,
                onValueChange = { newName ->
                     if (newName.length == 0) {
                        isNameError = true
                        newName
                    } else {
                        newName.get(newName.length - 1)
                        isNameError = false
                    }
                    nameState = newName
                 },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isNameError)Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },

                label = {
                    Text(
                        text = stringResource(id = R.string.name),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Black,
                        fontSize = 18.sp,
                    )
                },
                isError = isNameError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = emailState,
                onValueChange = { newEmail ->
                    if (newEmail.length == 0) {
                        isEmailError = true
                        newEmail
                    } else {
                        newEmail.get(newEmail.length - 1)
                        isEmailError = false
                    }
                    emailState = newEmail },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isEmailError)Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.email),
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                },
                isError = isEmailError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))



            OutlinedTextField(
                value = passwordState,
                onValueChange = { newPassword ->
                    if (newPassword.length == 0) {
                        isPasswordError = true
                        newPassword
                    } else {
                        newPassword.get(newPassword.length - 1)
                        isPasswordError = false
                    }
                    passwordState = newPassword },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isPasswordError)Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.password),
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                },
                isError = isPasswordError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = birthDateState,
                onValueChange = { newBirthDate ->
                    if (newBirthDate.length == 0) {
                        isBirthDateError = true
                        newBirthDate
                    } else {
                        newBirthDate.get(newBirthDate.length - 1)
                        isBirthDateError = false
                    }
                    birthDateState = newBirthDate },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isBirthDateError) Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.birthdate),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Black,
                        fontSize = 18.sp,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isBirthDateError,
                visualTransformation = DateTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))


            OutlinedTextField(
                value = cpfState,
                onValueChange = { newCpf ->
                    if (newCpf.length == 0) {
                        isCpfError = true
                        newCpf
                    } else {
                        newCpf.get(newCpf.length - 1)
                        isCpfError = false
                    }
                    cpfState = newCpf },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isCpfError)Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.cpf),
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isCpfError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(),
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = stringResource(id = R.string.gender),
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 24.sp,
                color = Color.White)

            val radioOptions = listOf("Masculino", "Feminino", "Prefiro não informar")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
            Column (Modifier.selectableGroup()) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(start = 6.dp),
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = cepState,
                onValueChange = { newCep ->
                    if (newCep.length == 0) {
                        isCepError = true
                        newCep
                    } else {
                        newCep.get(newCep.length - 1)
                        isCepError = false
                    }
                    cepState = newCep },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isCepError)Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.cep),
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isCepError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = stateState,
                onValueChange = { newState ->
                    if (newState.length == 0) {
                        isStateError = true
                        newState
                    } else {
                        newState.get(newState.length - 1)
                        isStateError = false
                    }
                    stateState = newState },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isStateError)Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.state),
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                },
                isError = isStateError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = cityState,
                onValueChange = { newCity ->
                    if (newCity.length == 0) {
                        isCityError = true
                        newCity
                    } else {
                        newCity.get(newCity.length - 1)
                        isCityError = false
                    }
                    cityState = newCity },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isCityError)Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.city),
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                },
                isError = isCityError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = numberState,
                onValueChange = { newNumber ->
                    if (newNumber.length == 0) {
                        isNumberError = true
                        newNumber
                    } else {
                        newNumber.get(newNumber.length - 1)
                        isNumberError = false
                    }
                    numberState = newNumber },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isNumberError)Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.number),
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isNumberError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
               onClick = { isNameError = nameState.length == 0
                    isEmailError = emailState.length == 0
                    isPasswordError = passwordState.length == 0
                    isCpfError = cpfState.length == 0
                    isBirthDateError = birthDateState.length == 0
                    isCepError = cepState.length == 0
                    isStateError = stateState.length == 0
                    isCityError = cityState.length == 0
                    isNumberError = numberState.length == 0

                    if (isNameError == true && isEmailError == true && isPasswordError == true && isCpfError == true  && isBirthDateError == true && isCepError == true && isStateError == true && isCityError == true && isNumberError == true) {
                        
                    }else{

                    }
          },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(2f)
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomEnd = 32.dp, bottomStart = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(red = 79, green = 254, blue = 199, alpha = 255)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.send),
                    fontSize = 18.sp,
                    color = Color(red = 79, green = 121, blue = 254, alpha = 255)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Preview
@Composable
fun CadastroUserPreview(){
    CadastroUser()
}
