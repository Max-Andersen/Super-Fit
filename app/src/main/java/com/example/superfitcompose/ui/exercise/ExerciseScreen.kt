package com.example.superfitcompose.ui.exercise

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.superfitcompose.R
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.ui.theme.SuperFitComposeTheme


@Composable
fun ExerciseScreen(navController: NavController, viewModel: ExerciseViewModel = viewModel()) {
    LaunchedEffect(key1 = true) {
        viewModel.processIntent(ExerciseIntent.LoadExerciseData(TrainingType.PLANK))
        Log.d("!!!", "LOAD TIME")
    }

    SuperFitComposeTheme {
        val screenState by viewModel.getScreenState().observeAsState(ExerciseViewState())

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = "Plank",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 56.dp)
                )
                ExerciseCounter(
                    screenState.counter,
                    if (screenState.beginCounterValue == 0) 1 else screenState.beginCounterValue,
                    screenState.finished
                )

                Controllers(
                    screenState.pause,
                    screenState.finished,
                    onPause = { viewModel.processIntent(ExerciseIntent.PauseExercise) },
                    onStart = { viewModel.processIntent(ExerciseIntent.StartExercise) },
                    onStop = { viewModel.processIntent(ExerciseIntent.FinishExercise) }
                )
            }
        }
    }
}

@Composable
fun ExerciseCounter(
    counter: Int,
    counterBeginValue: Int,
    finished: Boolean,
) {

    val progressAngle by animateFloatAsState(
        targetValue = 360f / counterBeginValue.toFloat() * counter,
        animationSpec = tween(500)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 72.dp, end = 72.dp, top = 64.dp)
            .aspectRatio(1f)
    ) {
        ExerciseProgress(counter, finished)
        CircleProgress(angle = progressAngle)
    }
}

@Composable
fun ExerciseProgress(
    counter: Int,
    finished: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(23.dp)
            .background(color = Color.DarkGray, shape = CircleShape)
            .clip(CircleShape),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (finished) {
            if (counter <= 0) {
                Image(
                    painter = painterResource(id = R.drawable.complete_tick),
                    contentDescription = null,
                )
            } else {
                Text(
                    text = "$counter times are missing.\n" +
                            "You can do it better!",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Text(
                text = "$counter",
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = stringResource(id = R.string.exercise_seconds_left),
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 16.sp
            )
        }

    }
}


@Composable
internal fun CircleProgress(
    angle: Float,
) {
    Box(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .aspectRatio(1f)
            .drawBehind {
                drawArc(
                    color = Color.DarkGray,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 10f)
                )
                drawArc(
                    color = Color(0xFFB461F5),
                    startAngle = -90f,
                    sweepAngle = angle,
                    useCenter = false,
                    style = Stroke(width = 10f, cap = StrokeCap.Round)
                )
            }
    )
}


@Preview
@Composable
fun ExerciseScreenPreview() {
    ExerciseScreen(navController = rememberNavController())
}
