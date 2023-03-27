package br.senai.sp.jandira.doetempo


import RetrofitFactoryLogin
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.compose.ui.Alignment
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
import br.senai.sp.jandira.doetempo.model.LoginDto
import br.senai.sp.jandira.doetempo.model.TokenDto
import br.senai.sp.jandira.doetempo.services.login.AuthApiService
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import okhttp3.internal.http2.Settings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


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

    var isPasswordError by remember {
        mutableStateOf(false)
    }



//    class LoginViewModel : ViewModel() {
//        val isSuccessLoading = mutableStateOf(value = false)
//        val imageErrorAuth = mutableStateOf(value = false)
//        val progressBar = mutableStateOf(value = false)
//        private val loginRequestLiveData = MutableLiveData<Boolean>()
//        fun login(emailState: String, passwordState: String) {
//            viewModelScope.launch(Dispatchers.IO) {
//                try {
//                    progressBar.value = true
//                    val authService = RetrofitFactoryLogin.RetrofitHelper.getAuthService()
//                    val responseService =
//                        authService.getLogin(
//                            LoginDto(
//                                email = emailState,
//                                password = passwordState
//                            )
//                        )
//                    if (responseService.isSuccessful) {
//                        delay(1500L)
//                        isSuccessLoading.value = true
//                        responseService.body()?.let { tokenDto ->
//                            Log.d("Logging", "Response TokenDto:$tokenDto")
//                        }
//                    } else {
//                        responseService.errorBody()?.let { error ->
//                            imageErrorAuth.value = true
//                            delay(1500L)
//                            imageErrorAuth.value = false
//                            error.close()
//                        }
//                    }
//                    loginRequestLiveData.postValue(responseService.isSuccessful)
//                    progressBar.value = false
//                } catch (e: Exception) {
//                    Log.d("Logging", "Error Authentication", e)
//                    progressBar.value = false
//                }
//            }
//        }
//    }

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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
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

//            fun NavigationScreen(viewModel: LoginViewModel) {
//                val navController = rememberNavController()
//                val loadingProgressBar = viewModel.progressBar.value
//                val imageError = viewModel.imageErrorAuth.value
//                NavHost(
//                    navController = navController,
//                    startDestination = ComposeNavigator.Destination.getStartDestination()
//                ) {
//                    composable(route = ComposeNavigator.Destination.Login.route) {
//                        if (viewModel.isSuccessLoading.value) {
//                            LaunchedEffect(key1 = Unit) {
//                                navController.navigate(route =
//                                ComposeNavigator.Destination.CampanhaDetailsActivity.route) {
//                                    popUpTo(route = ComposeNavigator.Destination.Login.route) {
//                                        inclusive = true
//                                    }
//                                }
//                            }
//                        } else {
//                            Login(
//
//                            )
//                        }
//                    }
//                    composable(route = ComposeNavigator.Destination.CampanhaDetailsActivity.route) {
//                        CampanhaDetailsActivity()
//                    }
//                }
//            }


            val context = LocalContext.current
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

                        authToken.enqueue(object: Callback<TokenDto> {
                            override fun onResponse(
                                call: Call<TokenDto>,
                                response: Response<TokenDto>
                            ) {
                                Log.i("ds3m", response.body().toString())

                                if (response.body()?.accessTokenVerify.isNullOrEmpty()) {
                                    Toast.makeText(context, "Usuário e/ou senha incorretos!", Toast.LENGTH_SHORT).show()
                                    Log.i("ds3m", "Login inválido!")
                                }

                                if (response.body()?.dataUser?.type == "ONG") {
                                    // Home da ong
                                    val newActivity = Intent(context, FirstPageActivity::class.java).putExtra("key", response.body()!!.accessTokenVerify)
                                    startActivity(context, newActivity, Bundle.EMPTY)
                                }
                            }

                            override fun onFailure(call: Call<TokenDto>, t: Throwable) {
                                TODO("Not yet implemented")
                            }

                        })

//                        fun authenticate(email: String, password: String) {
//                            val url = "http://10.0.2.2:3333/auth/"
//                            val body = "grant_type=password&username=${email}&password=${password}"
//                            val request = Request.Builder()
//                                .url(url)
//                                .post(body)
//                                .build()
//                            val client = OkHttpClient()
//                            val response = client.newCall(request).execute()
//                            val responseBody = response.body?.string()
//
//                            if (responseBody != null) {
//                                if (responseBody.contains("authentication successful")) {
//                                    context.startActivity(Intent(context, CampanhaDetailsActivity::class.java))
//                                } else {
//                                    Log.d("Logging", "Error Authentication")
//                                }
//                            }
//                        }
//
//                        val email = userState.onSave { email: String? ->
//                            if (email != null) {
//                                authenticate(email)
//                            }
//                        }
//
//                        val password = passwordState.onSave { password: String? ->
//                            if (password != null) {
//                                authenticate(password)
//                            }
//                        }
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
