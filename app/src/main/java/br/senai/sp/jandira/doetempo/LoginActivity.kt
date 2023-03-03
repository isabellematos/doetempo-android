package br.senai.sp.jandira.doetempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Login()
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun Login() {

    var userState by rememberSaveable {
        mutableStateOf("")
    }
    var forgetPassword by remember {
        mutableStateOf(false)
    }

    var isUserError by remember {
        mutableStateOf(false)
    }

    var passwordState by rememberSaveable {
        mutableStateOf("")
    }

    var isPasswordError by remember {
        mutableStateOf(false)
    }


    //Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.login),
                contentScale = ContentScale.Crop
            ),
        verticalArrangement = Arrangement.Top

    ) {
        //Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                painter = painterResource(id = R.drawable.doetempologo),
                contentDescription = "logo",
                modifier = Modifier.size(250.dp)
            )
        }

        //Form
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            OutlinedTextField(
                value = userState,
                onValueChange = { newUser ->
                    if (newUser.length == 0) {
                        isUserError = true
                        newUser
                    } else {
                        newUser.get(newUser.length - 1)
                        isUserError = false
                    }
                    userState = newUser
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 80.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.user),
                        modifier = Modifier.zIndex(1f),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,


                        )
                },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy()
                )
            )
            OutlinedTextField(
                value = passwordState,
                onValueChange = {newPassword ->
                    if (newPassword.length == 0) {
                        isPasswordError = true
                        newPassword
                    } else {
                        newPassword.get(newPassword.length - 1)
                        isPasswordError = false
                    }
                    passwordState = newPassword
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.password),
                        modifier = Modifier.zIndex(1f),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy()
                )
            )
            Text(
                text = stringResource(id = R.string.forgotPassword),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 200.dp)
                    .padding(top = 16.dp),
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline,
                color = Color.Blue
            )
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(280.dp)
                    .align(alignment = CenterHorizontally)
                    .padding(top = 100.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(79, 254, 199)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.enter)
                )
            }
        }

    }
}