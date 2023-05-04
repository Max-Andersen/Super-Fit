package com.example.superfitcompose.ui.auth.code

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superfitcompose.R
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.OrbitalScope
import com.skydoves.orbital.animateSharedElementTransition
import com.skydoves.orbital.rememberContentWithOrbitalScope

@Composable
fun EnterCodeScreen(email: String, navController: NavController, viewModel: CodeInputViewModel = viewModel()) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.auth_screen_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = R.string.super_fit),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(start = 37.dp, top = 44.dp, end = 37.dp)
                .wrapContentHeight(align = Alignment.Top)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        val viewState by viewModel.getScreenState().observeAsState(CodeEnterViewState())

        if (viewState.errorMessage.isNotEmpty()) {
            Toast.makeText(LocalContext.current, viewState.errorMessage, Toast.LENGTH_LONG).show()
            viewModel.processIntent(CodeInputScreenIntent.ErrorProcessed)
        }


        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 104.dp, end = 104.dp, top = 244.dp)
                .wrapContentWidth()
        )

        var data = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")


        val movementSpec = SpringSpec<IntOffset>(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 200f
        )

        val transformationSpec = SpringSpec<IntSize>(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 200f
        )

        val isTransformed = rememberSaveable { mutableStateOf(false) }

        val items = rememberContentWithOrbitalScope {
            Column(
                Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.Bottom),
                verticalArrangement = Arrangement.spacedBy(21.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    GetCodeCard(
                        item = data[0],
                        sendIntent = viewModel::processIntent,
                        isTransformed = isTransformed,
                        movementSpec = movementSpec,
                        transformationSpec = transformationSpec,
                        orbitalScope = this@rememberContentWithOrbitalScope
                    )
                    GetCodeCard(
                        item = data[1],
                        sendIntent = viewModel::processIntent,
                        isTransformed = isTransformed,
                        movementSpec = movementSpec,
                        transformationSpec = transformationSpec,
                        orbitalScope = this@rememberContentWithOrbitalScope
                    )
                    GetCodeCard(
                        item = data[2],
                        sendIntent = viewModel::processIntent,
                        isTransformed = isTransformed,
                        movementSpec = movementSpec,
                        transformationSpec = transformationSpec,
                        orbitalScope = this@rememberContentWithOrbitalScope
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    GetCodeCard(
                        item = data[3],
                        sendIntent = viewModel::processIntent,
                        isTransformed = isTransformed,
                        movementSpec = movementSpec,
                        transformationSpec = transformationSpec,
                        orbitalScope = this@rememberContentWithOrbitalScope
                    )
                    GetCodeCard(
                        item = data[4],
                        sendIntent = viewModel::processIntent,
                        isTransformed = isTransformed,
                        movementSpec = movementSpec,
                        transformationSpec = transformationSpec,
                        orbitalScope = this@rememberContentWithOrbitalScope
                    )
                    GetCodeCard(
                        item = data[5],
                        sendIntent = viewModel::processIntent,
                        isTransformed = isTransformed,
                        movementSpec = movementSpec,
                        transformationSpec = transformationSpec,
                        orbitalScope = this@rememberContentWithOrbitalScope
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    GetCodeCard(
                        item = data[6],
                        sendIntent = viewModel::processIntent,
                        isTransformed = isTransformed,
                        movementSpec = movementSpec,
                        transformationSpec = transformationSpec,
                        orbitalScope = this@rememberContentWithOrbitalScope
                    )
                    GetCodeCard(
                        item = data[7],
                        sendIntent = viewModel::processIntent,
                        isTransformed = isTransformed,
                        movementSpec = movementSpec,
                        transformationSpec = transformationSpec,
                        orbitalScope = this@rememberContentWithOrbitalScope
                    )
                    GetCodeCard(
                        item = data[8],
                        sendIntent = viewModel::processIntent,
                        isTransformed = isTransformed,
                        movementSpec = movementSpec,
                        transformationSpec = transformationSpec,
                        orbitalScope = this@rememberContentWithOrbitalScope
                    )
                }
            }

        }

        Orbital(
            modifier = Modifier.padding(start = 38.dp, end = 38.dp, bottom = 107.dp),
            isTransformed = isTransformed.value,
            onStartContent = {
                Box(modifier = Modifier.fillMaxSize()) {
                    items()
                }

//            LazyVerticalGrid(
//                columns = GridCells.Fixed(3),
//                verticalArrangement = Arrangement.spacedBy(21.dp),
//                horizontalArrangement = Arrangement.spacedBy(25.dp),
//                modifier = Modifier
//
//            ) {
//
//            }

            },
            onTransformedContent = {
                data = data.shuffled()
                Box(modifier = Modifier.fillMaxSize()) {
                    items()
                }
            }
        )


    }



}



@Composable
fun GetCodeCard(
    item: String,
    sendIntent: (CodeInputScreenIntent) -> Unit,
    isTransformed: MutableState<Boolean>,
    movementSpec: SpringSpec<IntOffset>,
    transformationSpec: SpringSpec<IntSize>,
    orbitalScope: OrbitalScope
) {
    Card(
        modifier = Modifier
            .clickable {
                sendIntent(CodeInputScreenIntent.CodeNumberInput(item))
                isTransformed.value = !isTransformed.value
            }
            .size(78.dp)
            .animateSharedElementTransition(orbitalScope, movementSpec, transformationSpec),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(2.dp, Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item,
                style = MaterialTheme.typography.labelMedium
            )
        }

    }
}