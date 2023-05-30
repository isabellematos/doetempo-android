package br.senai.sp.jandira.doetempo

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.model.*
import br.senai.sp.jandira.doetempo.services.gender.GenderCall
import br.senai.sp.jandira.doetempo.services.user.UserCall
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import buscarCep
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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

    var cpfState by rememberSaveable() {
        mutableStateOf("")
    }

    var cepState by rememberSaveable() {
        mutableStateOf("")
    }

    var stateState by rememberSaveable() {
        mutableStateOf("")
    }

    var logradouroState by rememberSaveable() {
        mutableStateOf("")
    }

    var bairroState by rememberSaveable() {
        mutableStateOf("")
    }

    var genderStateString by rememberSaveable() {
        mutableStateOf("")
    }

    var genderId by rememberSaveable() {
        mutableStateOf("")
    }

    var complementoState by rememberSaveable() {
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

    var isBairroError by remember {
        mutableStateOf(false)
    }

    var isCepError by remember {
        mutableStateOf(false)
    }

    var isStateError by remember {
        mutableStateOf(false)
    }

    var isLogradouroError by remember {
        mutableStateOf(false)
    }

    var isComplementoError by remember {
        mutableStateOf(false)
    }

    var isCityError by remember {
        mutableStateOf(false)
    }

    var isNumberError by remember {
        mutableStateOf(false)
    }

    var resultState by remember {
        mutableStateOf("")
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
                    if (isNameError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },

                label = {
                    Text(
                        text = stringResource(id = R.string.name),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                isError = isNameError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy()
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
                    emailState = newEmail
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isEmailError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.email),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                isError = isEmailError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
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
                    passwordState = newPassword
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isPasswordError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.password),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = isPasswordError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
                )
            )

            Spacer(modifier = Modifier.height(32.dp))


            val mContext = LocalContext.current


            val mYear: Int
            val mMonth: Int
            val mDay: Int


            val mCalendar = Calendar.getInstance()

            mYear = mCalendar.get(Calendar.YEAR)
            mMonth = mCalendar.get(Calendar.MONTH)
            mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

            mCalendar.time = Date()

            val birthDateState = remember { mutableStateOf("") }

            var mDatePickerDialog = DatePickerDialog(
                mContext,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    birthDateState.value =
                        "$mYear-${if (mMonth + 1 < 10) "0" + (mMonth + 1) else (mMonth + 1)}-${if (mDayOfMonth < 10) "0" + mDayOfMonth else mDayOfMonth}"
                }, mYear, mMonth, mDay
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(onClick = {
                    mDatePickerDialog.show()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(79, 254, 199))) {
                    Text(text = "Insira sua data de nascimento", color = Color.Black)
                }

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Data Selecionada: ${birthDateState.value}",
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center

                )
            }

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
                    cpfState = newCpf
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isCpfError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.cpf),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isCpfError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.gender),
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 24.sp,
                color = Color.White
            )

            var genderList by remember {
                mutableStateOf(listOf<Gender>())
            }

            var allGenders by rememberSaveable {
                mutableStateOf(listOf(Gender()))
            }

            val retrofit = RetrofitFactory.getRetrofit()
            val genderCreateCall = retrofit.create(GenderCall::class.java)
            val genderCall = genderCreateCall.getAll()

            genderCall.enqueue(object : Callback<GenderList> {
                override fun onResponse(call: Call<GenderList>, response: Response<GenderList>) {
//                    Log.i("ds3m", response.body()!!.genders[0].name)
                    genderList = response.body()!!.genders
                    genderStateString = response.body()!!.genders[0].id.toString()
                    Log.i("ididid", genderStateString.toString())
                }

                override fun onFailure(call: Call<GenderList>, t: Throwable) {
//                    Log.i("ds3m", t.message.toString())
                }

            })

            var genderState by remember {
                mutableStateOf(Gender())
            }


           // var genderStateAbbreviation = genderState.toString()

            if (!genderList.isEmpty()) {
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(genderList[0]) }
                Column(Modifier.selectableGroup()) {
                    genderList.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        onOptionSelected(text)
                                        genderStateString = text.id.toString()
                                        Log.i("genderid", genderStateString.toString())
//                                        Log.i("ds3m", genderState)
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = { genderStateString = text.id.toString() }
                            )
                            text.name?.let { Text(text = it) }
                        }
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

                    cepState = newCep
                    if (newCep.length == 8) {
//                        Log.i("ds3m", newCep)
                        buscarCep(newCep) {
                            cityState = it.cidade.toString()
                            logradouroState = it.logradouro.toString()
                            stateState = it.estado.toString()
                            bairroState = it.bairro.toString()
                            cepState = it.cep.toString()
                        }.toString()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isCepError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.cep),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isCepError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
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
                    stateState = newState
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isStateError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.state),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                isError = isStateError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
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
                    cityState = newCity
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isCityError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.city),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                isError = isCityError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = logradouroState,
                onValueChange = { newState ->
                    if (newState.length == 0) {
                        isLogradouroError = true
                        newState
                    } else {
                        newState.get(newState.length - 1)
                        isLogradouroError = false
                    }
                    logradouroState = newState
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isLogradouroError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.street),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                isError = isLogradouroError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = bairroState,
                onValueChange = { newState ->
                    if (newState.length == 0) {
                        isBairroError = true
                        newState
                    } else {
                        newState.get(newState.length - 1)
                        isBairroError = false
                    }
                    bairroState = newState
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isBairroError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.bairro),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                isError = isBairroError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = complementoState,
                onValueChange = { newState ->
                    if (newState.length == 0) {
                        isComplementoError = true
                        newState
                    } else {
                        newState.get(newState.length - 1)
                        isComplementoError = false
                    }
                    complementoState = newState
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isComplementoError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.complemento),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                isError = isComplementoError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
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
                    numberState = newNumber
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isNumberError) Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.number),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isNumberError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy(),
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            val context = LocalContext.current

            genderStateString = genderId

            Button(
                onClick = {
                    isNameError = nameState.length == 0
                    isEmailError = emailState.length == 0
                    isPasswordError = passwordState.length == 0
                    isCpfError = cpfState.length == 0
                    //isBirthDateError = birthDateState.length == 0
                    isCepError = cepState.length == 0
                    isStateError = stateState.length == 0
                    isCityError = cityState.length == 0
                    isNumberError = numberState.length == 0

                    val text = "Todos os campos são necessarios"
                    val duration = Toast.LENGTH_SHORT

                    if (isNameError || isEmailError || isPasswordError || isCpfError || isBirthDateError || isCepError || isStateError || isCityError || isNumberError) {
                        val toast = Toast.makeText(context, text, duration)
                        toast.show()
                    } else {
                        val contact = CreateUser(
                            name = nameState,
                            email = emailState,
                            password = passwordState,
                            birthdate = LocalDate.parse(
                                birthDateState.value,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            ).toString(),
                            cpf = cpfState,

                                address = Address(
                                number = numberState,
                                postalCode = cepState,
                                complement = null
                                ),
                            gender = genderStateString,
                            type = Type()
                        )


                        val retrofit = RetrofitFactory.getRetrofit()
                        val userCall = retrofit.create(UserCall::class.java)
                        val callContactPost = userCall.save(contact)

                        callContactPost.enqueue(object : Callback<CreatedUser> {
                            override fun onResponse(
                                call: Call<CreatedUser>,
                                response: Response<CreatedUser>
                            ) {
                                Log.i("ds3m", response.body()!!.toString())

                                context.startActivity(Intent(context, LoginActivity::class.java))

                            }
                            override fun onFailure(call: Call<CreatedUser>, t: Throwable) {
                                Log.i("ds3m", t.message.toString())
                            }
                        })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(2f)
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp,
                    bottomEnd = 32.dp,
                    bottomStart = 32.dp
                ),
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
fun CadastroUserPreview() {
    CadastroUser()
}
