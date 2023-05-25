package com.example.superfitcompose.ui.main.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.superfitcompose.R
import com.example.superfitcompose.bottomPadding
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.ui.Routes
import com.example.superfitcompose.ui.shared.ExerciseCard
import com.example.superfitcompose.ui.theme.SuperFitComposeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllExercisesScreen(
    navController: NavController,
    viewModel: AllExercisesViewModel = koinViewModel()
) {

    val screenState by viewModel.getScreenState().observeAsState(AllExercisesViewState())

    if (screenState.navigateToTrainingType != null) {
        val destination = screenState.navigateToTrainingType
        viewModel.processIntent(AllExercisesIntent.NavigationProcessed)
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

        AllExercisesScreenFilling(viewModel::processIntent)

    }
}


@Composable
fun AllExercisesScreenFilling(cardClicked: (AllExercisesIntent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.exercises),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        ExerciseCard(TrainingType.PUSH_UP) {
            cardClicked(
                AllExercisesIntent.ClickedOnExercise(
                    TrainingType.PUSH_UP
                )
            )
        }
        ExerciseCard(TrainingType.PLANK) {
            cardClicked(
                AllExercisesIntent.ClickedOnExercise(
                    TrainingType.PLANK
                )
            )
        }
        ExerciseCard(TrainingType.SQUATS) {
            cardClicked(
                AllExercisesIntent.ClickedOnExercise(
                    TrainingType.SQUATS
                )
            )
        }
        ExerciseCard(TrainingType.CRUNCH) {
            cardClicked(
                AllExercisesIntent.ClickedOnExercise(
                    TrainingType.CRUNCH
                )
            )
        }
        ExerciseCard(TrainingType.RUNNING) {
            cardClicked(
                AllExercisesIntent.ClickedOnExercise(
                    TrainingType.RUNNING
                )
            )
        }

        Spacer(modifier = Modifier.size(bottomPadding * 2))
    }
}


@Preview
@Composable
fun AllExercisesScreenPreview() {
    SuperFitComposeTheme {
        AllExercisesScreen(navController = rememberNavController())
    }
}