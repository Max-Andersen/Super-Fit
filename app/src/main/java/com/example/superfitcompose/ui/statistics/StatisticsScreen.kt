package com.example.superfitcompose.ui.statistics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.superfitcompose.R
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.ui.statistics.graphs.PlaceTrainGraph
import com.example.superfitcompose.ui.statistics.graphs.PlaceWeightGraph
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: StatisticsViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.processIntent(StatisticsIntent.LoadData)
    }

    val viewState by viewModel.getViewState().observeAsState(StatisticsViewState())

    if (viewState.navigateBack) {
        viewModel.processIntent(StatisticsIntent.NavigationProcessed)
        navController.navigateUp()
    }


    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.secondary) {

        Image(
            painter = painterResource(id = R.drawable.statistic_bg),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Scaffold(
            modifier = Modifier
                .padding(start = 20.dp),
            containerColor = Color.Transparent,
            topBar = {
                Box(modifier = Modifier.padding(top = 40.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.left),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { viewModel.processIntent(StatisticsIntent.ClickedOnNavigateBack) }
                    )
                }
            }
        ) { paddings ->

            Column(
                modifier = Modifier
                    .padding(paddings)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier.padding(top = 13.dp),
                    text = stringResource(id = R.string.weight_text),
                    style = MaterialTheme.typography.headlineSmall
                )

                PlaceWeightGraph(viewState.weightHistory)

                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(id = R.string.training),
                    style = MaterialTheme.typography.headlineSmall
                )

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 22.dp)
                ) {
                    PlaceExerciseButtons(viewState.selectedExercise, viewModel::processIntent)
                }

                PlaceTrainGraph(
                    viewState.selectedExercise,
                    viewState.pushUpsHistory,
                    viewState.plankHistory,
                    viewState.crunchHistory,
                    viewState.squatsHistory,
                    viewState.runningHistory
                )
            }
        }
    }
}


@Composable
fun PlaceExerciseButtons(selectedExercise: TrainingType, sendIntent: (StatisticsIntent) -> Unit) {
    ExerciseButton(
        selectedExercise == TrainingType.PUSH_UP,
        TrainingType.PUSH_UP
    ) { sendIntent(StatisticsIntent.SelectPushUpsHistory) }
    ExerciseButton(
        selectedExercise == TrainingType.PLANK,
        TrainingType.PLANK
    ) { sendIntent(StatisticsIntent.SelectPlankHistory) }
    ExerciseButton(
        selectedExercise == TrainingType.CRUNCH,
        TrainingType.CRUNCH
    ) { sendIntent(StatisticsIntent.SelectCrunchHistory) }
    ExerciseButton(
        selectedExercise == TrainingType.SQUATS,
        TrainingType.SQUATS
    ) { sendIntent(StatisticsIntent.SelectSquatsHistory) }
    ExerciseButton(
        selectedExercise == TrainingType.RUNNING,
        TrainingType.RUNNING
    ) { sendIntent(StatisticsIntent.SelectRunningHistory) }
}


@Composable
fun ExerciseButton(isSelected: Boolean, type: TrainingType, click: () -> Unit) {

    val text = when (type) {
        TrainingType.PUSH_UP -> R.string.push_ups
        TrainingType.PLANK -> R.string.plank
        TrainingType.CRUNCH -> R.string.crunch
        TrainingType.SQUATS -> R.string.squats
        TrainingType.RUNNING -> R.string.running
    }

    OutlinedButton(
        onClick = { click() },
        modifier = Modifier.padding(end = 20.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(
                alpha = 0.08f
            ) else MaterialTheme.colorScheme.secondary,
            contentColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.White.copy(
                alpha = 0.87f
            )
        ),
        border = BorderStroke(
            1.dp,
            if (isSelected) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.12f)
        )
    ) {
        Text(text = stringResource(id = text))
    }
}