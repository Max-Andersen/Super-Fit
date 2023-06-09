package com.example.superfitcompose.ui.main.mainscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.superfitcompose.R
import com.example.superfitcompose.bottomPadding
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.models.BodyParameters
import com.example.superfitcompose.ui.Routes
import com.example.superfitcompose.ui.shared.ExerciseCard
import com.example.superfitcompose.ui.theme.SuperFitComposeTheme
import com.example.superfitcompose.ui.theme.greyTint
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel = koinViewModel()) {

    LaunchedEffect(key1 = true) {
        viewModel.processIntent(MainScreenIntent.LoadData)
    }

    val viewState by viewModel.getViewState().observeAsState(MainScreenViewState())

    if (viewState.navigateToMyBody) {
        viewModel.processIntent(MainScreenIntent.NavigationProcessed)
        navController.navigate(Routes.MY_BODY_DETAILS)
    }

    if (viewState.seeAllExercises) {
        viewModel.processIntent(MainScreenIntent.NavigationProcessed)
        navController.navigate(Routes.ALL_EXERCISES)
    }

    if (viewState.signOut) {
        viewModel.processIntent(MainScreenIntent.NavigationProcessed)
        navController.navigate(Routes.LOGIN){
            popUpTo(
                navController.graph.id
            )
        }
    }

    if (viewState.navigateToTrainingType != null) {
        val destination = viewState.navigateToTrainingType
        viewModel.processIntent(MainScreenIntent.NavigationProcessed)
        navController.navigate(Routes.EXERCISE + "/" + destination)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.main_screen_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(
                        Color.White, shape = RoundedCornerShape(
                            topStart = 24.dp, topEnd = 24.dp
                        )
                    )
            )
        }

        MainScreenFilling(viewState.bodyParams, viewState.lastTrainings, viewModel::processIntent)

    }
}


@Composable
fun MainScreenFilling(
    bodyParameters: BodyParameters?,
    lastTrainings: Pair<TrainingType?, TrainingType?>?,
    sendIntent: (MainScreenIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        Text(
            text = stringResource(id = R.string.my_body),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
        MyBodyCard(
            bodyParameters?.weight,
            bodyParameters?.height
        ) { sendIntent(MainScreenIntent.ClickedOnMyBodyCard) }

        Spacer(modifier = Modifier.size(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.last_exercises),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            Text(
                text = stringResource(id = R.string.see_all),
                style = MaterialTheme.typography.bodySmall,
                color = greyTint,
                modifier = Modifier.clickable {
                    sendIntent(MainScreenIntent.ClickedOnSeeAllExercises)
                }
            )

        }

        PlaceLastTrainings(lastTrainings, sendIntent)

        Spacer(modifier = Modifier.weight(1f))

        PlaceSignOutBlock(sendIntent)
    }
}

@Composable
fun PlaceSignOutBlock(sendIntent: (MainScreenIntent) -> Unit) {
    Row(
        modifier = Modifier
            .padding(bottom = 34.dp + bottomPadding)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.Bottom)
            .clickable { sendIntent(MainScreenIntent.ClickedOnSignOut) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.left),
            colorFilter = ColorFilter.tint(color = Color.Black),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(id = R.string.sign_out),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
    }
}

@Composable
fun PlaceLastTrainings(
    lastTrainings: Pair<TrainingType?, TrainingType?>?,
    sendIntent: (MainScreenIntent) -> Unit
) {
    if (lastTrainings != null) {
        lastTrainings.first?.let { type ->
            ExerciseCard(type = type) {
                sendIntent(
                    MainScreenIntent.ClickedOnExercise(
                        trainingType = type
                    )
                )
            }
        }

        lastTrainings.second?.let { type ->
            ExerciseCard(type = type) {
                sendIntent(
                    MainScreenIntent.ClickedOnExercise(
                        trainingType = type
                    )
                )
            }
        }
    } else {
        ExerciseCard(type = TrainingType.PUSH_UP) {
            sendIntent(
                MainScreenIntent.ClickedOnExercise(
                    trainingType = TrainingType.PUSH_UP
                )
            )
        }

        ExerciseCard(type = TrainingType.PLANK) {
            sendIntent(
                MainScreenIntent.ClickedOnExercise(
                    trainingType = TrainingType.PLANK
                )
            )
        }
    }
}

@Composable
fun MyBodyCard(weight: Int?, height: Int?, clicked: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { clicked() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            var sizeInDp by remember { mutableStateOf(DpSize.Zero) }
            val density = LocalDensity.current

            Image(
                painter = painterResource(id = R.drawable.mask_group_my_body),
                contentDescription = null,
                modifier = Modifier.onSizeChanged {
                    sizeInDp = density.run {
                        DpSize(
                            it.width.toDp(),
                            it.height.toDp()
                        )
                    }
                }
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = sizeInDp.height)
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.weight),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = weight?.let {
                            stringResource(id = R.string.weight).format(it.toString())
                        } ?: stringResource(id = R.string.undefined),
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.height),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = height?.let {
                            stringResource(id = R.string.height).format(it.toString())
                        } ?: stringResource(id = R.string.undefined),
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))


                Row(
                    modifier = Modifier
                        .wrapContentHeight(Alignment.Bottom),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.details),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.little_right),
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    SuperFitComposeTheme {
        MainScreen(navController = rememberNavController())
    }
}