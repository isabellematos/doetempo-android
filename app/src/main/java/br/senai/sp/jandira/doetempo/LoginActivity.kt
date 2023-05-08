package br.senai.sp.jandira.doetempo


import RetrofitFactoryLogin
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.startActivity
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.CampanhaList
import br.senai.sp.jandira.doetempo.model.LoginDto
import br.senai.sp.jandira.doetempo.model.TokenDto
import br.senai.sp.jandira.doetempo.services.login.AuthApiService
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
    val context = LocalContext.current

    var isPasswordError by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)


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
            horizontalAlignment = CenterHorizontally

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
                    if (newUser.isEmpty()) {
                        isUserError = true
                        newUser
                    } else {
                        newUser.get(newUser.length - 1)
                        isUserError = false
                    }
                    userState = newUser
                },
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 80.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.email),
                        modifier = Modifier.zIndex(1f),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        )
                },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy()
                )
            )
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
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 24.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.password),
                        modifier = Modifier.zIndex(1f),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent.copy()
                )
            )
            val context = LocalContext.current
            var campanhasState by remember {
                mutableStateOf(CampanhaList(listOf()))
            }
            Button(
                onClick = {
                    isUserError = userState.length == 0
                    isPasswordError = passwordState.length == 0

                    val text = "Todos os campos são necessarios"
                    val duration = Toast.LENGTH_SHORT

                    if (isUserError == true && isPasswordError == true) {
                        val toast = Toast.makeText(context, text, duration)
                        toast.show()
                    }
                    else {

                        val loginDto = LoginDto(email = userState, password = passwordState)

                        val retrofit = RetrofitFactoryLogin.getRetrofit()
                        val authCall = retrofit.create(AuthApiService::class.java)
                        val authToken = authCall.getLogin(loginDto)

                        authToken.enqueue(object : Callback<TokenDto> {
                            override fun onResponse(
                                call: Call<TokenDto>,
                                response: Response<TokenDto>
                            ) {
                                val token = response.body()?.accessTokenVerify.toString()

                                Log.i("testee", response.body().toString() )
                                if (response.body()?.accessTokenVerify.isNullOrEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Usuário e/ou senha incorretos!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.i("ds3m", "Login inválido!")
                                }

                                if (response.body()?.dataUser?.type == "ONG") {
                                    // Home da ong
                                    val newActivity =
                                        Intent(context, HomeActivity::class.java).putExtra(
                                            "key",
                                            response.body()!!.accessTokenVerify
                                        )
                                    newActivity.putExtra(
                                        "name",
                                        response.body()!!.dataUser?.name
                                    )

                                    newActivity.putExtra("id_user", response.body()!!.dataUser?.id)

                                    startActivity(context, newActivity, Bundle.EMPTY)

                                }
                                if (response.body()?.dataUser?.type == "USER") {
                                    // Home do voluntario
                                    val newActivity =
                                        Intent(context, HomeActivity::class.java).putExtra(
                                            "key",
                                            response.body()!!.accessTokenVerify
                                        )
                                    newActivity.putExtra(
                                        "name",
                                        response.body()!!.dataUser?.name
                                    )

                                    newActivity.putExtra("id_user", response.body()!!.dataUser?.id)
                                    newActivity.putExtra("type", response.body()!!.dataUser?.type)


                                    startActivity(context, newActivity, Bundle.EMPTY)

                                }
                            }

                            override fun onFailure(call: Call<TokenDto>, t: Throwable) {
                                Log.i("ds3m", t.message.toString())
                            }

                        })
                    }
                },
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
