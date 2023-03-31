package br.senai.sp.jandira.doetempo.HomeActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.model.Campanha
import br.senai.sp.jandira.doetempo.model.CampanhaList
import br.senai.sp.jandira.doetempo.model.UserList
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.campanha.CampanhaCall
import br.senai.sp.jandira.doetempo.services.user.UserCall
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import br.senai.sp.jandira.doetempo.CampanhaDetailsActivity
import br.senai.sp.jandira.doetempo.HomeActivity

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun cardCampanha(campanha: Campanha) {

    var titleState by remember {
        mutableStateOf("")
    }

    var descriptionState by remember {
        mutableStateOf("")
    }

    Column(
    ) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 12.dp, end = 12.dp)
                .clickable {
                    val newActivity = Intent(context, CampanhaDetailsActivity::class.java).putExtra("id", campanha.id)
                    ContextCompat.startActivity(context, newActivity, Bundle.EMPTY)
                }
                .size(width = 323.dp, height = 190.dp),


            backgroundColor = Color(244, 244, 244),
            shape = RoundedCornerShape(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = br.senai.sp.jandira.doetempo.R.drawable.luizamelllogo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(start = 12.dp, top = 12.dp)
                        .border(
                            2.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(shape = RoundedCornerShape(8.dp)),
                    alignment = Alignment.TopStart,

                    )
            }
            campanha.title?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(top = 12.dp, start = 10.dp),
                    color = Color(79, 121, 254),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold

                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sobre:",
                    modifier = Modifier.padding(start = 14.dp, top = 75.dp),
                    color = Color(79, 121, 254),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.size(8.dp))

                campanha.description?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 14.dp),
                        color = Color(136, 136, 136),
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
        }
    }
}




