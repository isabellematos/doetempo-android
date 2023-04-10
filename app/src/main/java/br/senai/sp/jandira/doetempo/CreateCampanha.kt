package br.senai.sp.jandira.doetempo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.doetempo.components.addCampanhaHeader
import br.senai.sp.jandira.doetempo.components.bottom
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class CreateCampanha : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoetempoTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(238, 238, 240)
                ) {
                    val systemUi = rememberSystemUiController()
                    SideEffect {
                        systemUi.setStatusBarColor(color = Color(238, 238, 240), darkIcons = true)
                    }
                    adcionarCampanha()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun adcionarCampanha() {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            //HEADER
            addCampanhaHeader()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {

                //MAIN
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
                CreateCampanhaScreen()
                    //middleInfos(viewModel())
                bottom(viewModel = CreateCampanhaViewModel())

            }
        }
    }


}
enum class MultiFloatingState{
    Expanded,
    Collapsed
}

class MinFabItem(
    val label: String,
    val identifier: String,
    val onClickAction: Unit

)


@Composable
fun Fab(
    multiFloatingState: MultiFloatingState,
    onMultiFabStateChange:(MultiFloatingState) -> Unit,
    items:List<MinFabItem>

) {
    val transition = updateTransition(targetState = multiFloatingState, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.Expanded) 315f else 0f
    }

    val fabScale by transition.animateFloat(label = "FabScale") {
        if ( it == MultiFloatingState.Expanded) 36f else 0f
    }

    val alpha by transition.animateFloat(
        label = "alpha",
        transitionSpec = { tween(durationMillis = 50) }
    ) {
        if(it == MultiFloatingState.Expanded) 1f else 0f
    }



    Column(
        horizontalAlignment = Alignment.End
    ) {
        if (transition.currentState == MultiFloatingState.Expanded){
            items.forEach{
                MinFab(
                    item = it,
                    onMinFabItemClick = {
                    },
                    alpha = alpha
                )
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
        FloatingActionButton(
            onClick = {
                onMultiFabStateChange(
                    if (transition.currentState == MultiFloatingState.Expanded){
                        MultiFloatingState.Collapsed
                    }else{
                        MultiFloatingState.Expanded
                    }
                )
            },
            backgroundColor = Color(157,231,253)
        ) {

            Icon(imageVector = Icons.Filled.Add,

                contentDescription = null,

                modifier = Modifier.rotate(rotate)
            )
        }
    }
}


@Composable
fun MinFab(
    item: MinFabItem,
    alpha: Float,
    ShowLabel:Boolean = true,
    onMinFabItemClick:(MinFabItem) -> Unit
) {
    Row {
        if (ShowLabel){
            Text(
                text = item.label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .alpha(
                        animateFloatAsState(
                            targetValue = alpha,
                            animationSpec = tween(50)
                        ).value
                    )
                    .padding()
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        Canvas(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    onClick = {
                        onMinFabItemClick.invoke(item)
                    },
                    indication = rememberRipple(
                        bounded = false,
                        radius = 20.dp,
                        color = Color(157, 231, 253)
                    ),
                ),

            ) {
            drawRoundRect(
                color = Color(157, 231, 253),
                cornerRadius = CornerRadius(10f),

                )
        }
    }
}
@Composable
fun DefaultPreview() {
    DoetempoTheme() {

    }
}