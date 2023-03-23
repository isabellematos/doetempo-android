package br.senai.sp.jandira.doetempo


import android.content.Context
import android.os.Bundle
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
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.senai.sp.jandira.doetempo.model.Login
import br.senai.sp.jandira.doetempo.services.login.LoginDao
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

@Database(entities = [(Login::class)], version = 1, exportSchema = false)
abstract class EmployeeRoomDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao

    companion object {
        /*The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
        This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
        It means that changes made by one thread to INSTANCE are visible to all other threads immediately.*/
        @Volatile
        private var INSTANCE: EmployeeRoomDatabase? = null

        fun getInstance(context: Context): EmployeeRoomDatabase {
            // only one thread of execution at a time can enter this block of code
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeRoomDatabase::class.java,
                        "employee_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
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
                    } else {
                        val contact = Login(
                            email = userState,
                            password = passwordState
                        )
                       // val callContactLogin = LoginCall.getAll(contact)
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