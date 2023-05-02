package br.senai.sp.jandira.doetempo.HomeActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.model.Campanha
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import br.senai.sp.jandira.doetempo.CampanhaDetailsActivity
import br.senai.sp.jandira.doetempo.datastore.DataStoreAppData
import br.senai.sp.jandira.doetempo.model.CampanhaDetalhes
import br.senai.sp.jandira.doetempo.model.OngList
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.campanha.CampanhaCall
import br.senai.sp.jandira.doetempo.services.ong.OngCall
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun cardCampanha(campanha: Campanha) {

    var titleState by remember {
        mutableStateOf("")
    }

    var idState by remember {
        mutableStateOf("")
    }

    var descriptionState by remember {
        mutableStateOf("")
    }

    var photoCampanhaState by remember {
        mutableStateOf("")
    }

    var photoCampanhaState1 by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(
    ) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 12.dp, end = 12.dp)
                .clickable {
                    val newActivity = Intent(context, CampanhaDetailsActivity::class.java).putExtra(
                        "id",campanha.id
                    )
                    ContextCompat.startActivity(context, newActivity, Bundle.EMPTY)
                }
                .size(width = 323.dp, height = 190.dp),


            backgroundColor = Color(244, 244, 244),
            shape = RoundedCornerShape(15.dp)
        ) {
            photoCampanhaState1 = campanha.ngo?.photo_url.toString()
            Log.i("photo", photoCampanhaState1)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                AsyncImage(
                    model = photoCampanhaState1,
                    contentDescription = null,
                    Modifier
                        .size(60.dp)
                        .padding(start = 12.dp, top = 12.dp)
                        .border(
                            2.dp,
                            color = Color(79, 121, 254),
                            shape = RoundedCornerShape(8.dp)
                        )
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




