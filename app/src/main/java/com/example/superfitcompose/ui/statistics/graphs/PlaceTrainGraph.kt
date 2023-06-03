package com.example.superfitcompose.ui.statistics.graphs

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.models.Training
import com.example.superfitcompose.ui.statistics.TrainGraph

@Composable
fun PlaceTrainGraph(
    selectedExercise: TrainingType,
    pushUpsHistory: List<Training>?,
    plankHistory: List<Training>?,
    crunchHistory: List<Training>?,
    squatsHistory: List<Training>?,
    runningHistory: List<Training>?
) {
    var yStep = 0
    var points = listOf<Float>()
    var yValues = listOf<Int>()
    var xValues = listOf<String>()

    when (selectedExercise) {
        TrainingType.PUSH_UP -> {
            yStep = 5
            points =
                pushUpsHistory?.flatMap { listOf(it.repeatCount.toFloat()) }
                    ?: listOf()
            yValues = listOf(5, 10, 15, 20, 25, 30)
            xValues = pushUpsHistory?.flatMap { listOf(it.date) }
                ?: listOf()

            Log.d("PushUps", "---")
            pushUpsHistory?.forEach {
                Log.d("PushUps", it.repeatCount.toString())
            }
        }

        TrainingType.PLANK -> {
            yStep = 5
            points =
                plankHistory?.flatMap { listOf(it.repeatCount.toFloat()) }
                    ?: listOf()
            yValues = listOf(5, 10, 15, 20, 25, 30)
            xValues = plankHistory?.flatMap { listOf(it.date) }
                ?: listOf()

            Log.d("Plank", "---")
            plankHistory?.forEach {
                Log.d("Plank", it.repeatCount.toString())
            }
        }

        TrainingType.CRUNCH -> {
            yStep = 5
            points =
                crunchHistory?.flatMap { listOf(it.repeatCount.toFloat()) }
                    ?: listOf()
            yValues = listOf(5, 10, 15, 20, 25, 30, 35, 40, 45, 50)
            xValues = crunchHistory?.flatMap { listOf(it.date) }
                ?: listOf()

            Log.d("Crunch", "---")
            crunchHistory?.forEach {
                Log.d("Crunch", it.repeatCount.toString())
            }
        }

        TrainingType.SQUATS -> {
            yStep = 5
            points =
                squatsHistory?.flatMap { listOf(it.repeatCount.toFloat()) }
                    ?: listOf()
            yValues = listOf(5, 10, 15, 20, 25)
            xValues = squatsHistory?.flatMap { listOf(it.date) }
                ?: listOf()

            Log.d("Squats", "---")
            squatsHistory?.forEach {
                Log.d("Squats", it.repeatCount.toString())
            }
        }

        TrainingType.RUNNING -> {
            yStep = 5
            points =
                runningHistory?.flatMap { listOf(it.repeatCount.toFloat()) }
                    ?: listOf()
            yValues = listOf(5, 10, 15, 20, 25)
            xValues = runningHistory?.flatMap { listOf(it.date) }
                ?: listOf()

            Log.d("Running", "---")
            runningHistory?.forEach {
                Log.d("Running", it.repeatCount.toString())
            }
        }
    }

    TrainGraph(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(top = 20.dp, end = 25.dp)
            .height(240.dp)
            .width(points.size * 80.dp),
        xValues = xValues,
        yValues = yValues,
        points = points,
        paddingSpace = 40.dp,
        verticalStep = yStep,
        gridColor = Color.White,
        lineColor = MaterialTheme.colorScheme.surface,
    )
}