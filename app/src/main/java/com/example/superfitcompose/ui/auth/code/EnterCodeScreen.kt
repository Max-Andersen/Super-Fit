package com.example.superfitcompose.ui.auth.code

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
fun EnterCodeScreen(
    email: String,
    navController: NavController,
    viewModel: CodeInputViewModel = viewModel()
) {

    LaunchedEffect(key1 = true){
        viewModel.processIntent(CodeInputScreenIntent.SetEmail(email))
    }

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

        if (viewState.navigateToMainScreen){
            Toast.makeText(LocalContext.current, "Navigate to main screen", Toast.LENGTH_SHORT).show()

        }


        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 104.dp, end = 104.dp, top = 244.dp)
                .wrapContentWidth()
        )

        SetCodeInputPlace(viewModel::processIntent)

    }
}

@Composable
fun SetCodeInputPlace(sendIntent: (CodeInputScreenIntent) -> Unit) {
    val numbers = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

    val isTransformed = rememberSaveable { mutableStateOf(false) }

    val items = mutableListOf<@Composable OrbitalScope.() -> Unit>()

    numbers.forEach { digit ->
        items.add(rememberContentWithOrbitalScope {
            GetCodeCard(
                digit,
                this
            ) {
                sendIntent(CodeInputScreenIntent.CodeNumberInput(digit))
                isTransformed.value = !isTransformed.value
            }
        })
    }

    Orbital(
        modifier = Modifier.padding(start = 38.dp, end = 38.dp, bottom = 107.dp),
        isTransformed = isTransformed.value,
        onStartContent = {
            items.shuffle()
            DisplayKeyboard(items = items, orbitalScope = this@Orbital)
        },
        onTransformedContent = {
            items.shuffle()
            DisplayKeyboard(items = items, this@Orbital)
        }
    )
}

@Composable
fun GetCodeCard(
    item: String,
    orbitalScope: OrbitalScope,
    clickEvent: () -> Unit
) {
    val movementSpec = SpringSpec<IntOffset>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = 200f
    )

    val transformationSpec = SpringSpec<IntSize>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = 200f
    )

    Card(
        modifier = Modifier
            .clickable {
                clickEvent()
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

@Composable
fun DisplayKeyboard(
    items: MutableList<@Composable (OrbitalScope) -> Unit>,
    orbitalScope: OrbitalScope
) {
    Column(
        Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.Bottom),
        verticalArrangement = Arrangement.spacedBy(21.dp)
    ) {
        for (i in 0..8 step 3) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items[i].invoke(orbitalScope)
                items[i + 1].invoke(orbitalScope)
                items[i + 2].invoke(orbitalScope)
            }
        }
    }
}


