package com.example.superfitcompose.ui.statistics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import com.example.superfitcompose.R
import com.example.superfitcompose.data.network.models.TrainingType
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
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

                if (viewState.weightHistory != null) {
                    val yStep = 20
                    val testPoints = listOf(120f, 125f, 100f, 50f, 80f, 25f, 130f, 90f, 120f, 60f)
                    val testX = (0..9).map {
                        val char = (it + 1).toString()
                        "$char-$char-$char"
                    }
                    val testY = (0..6).map { (it + 1) * yStep }

                    val xValues = viewState.weightHistory!!.flatMap { listOf(it.date) }
                    val yValues = (0..6).map { (it + 1) * yStep }
                    val points = viewState.weightHistory!!.flatMap { listOf(it.weight.toFloat()) }

                    val paddingSpace = 20.dp

                    WeightGraph(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(top = 25.dp, end = 25.dp)
                            .height(240.dp)
                            .width(points.size * 40.dp),
                        xValues = xValues,
                        yValues = yValues,
                        points = points,
                        paddingSpace = paddingSpace,
                        verticalStep = yStep,
                        gridColor = Color.White,
                        lineColor = MaterialTheme.colorScheme.surface,
                        pointColor = MaterialTheme.colorScheme.surface,
                    )


//                    Canvas(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .horizontalScroll(rememberScrollState())
//                            .padding(top = 20.dp)
//                            .height(250.dp)
//                            .width(2000.dp)
//                        //.defaultMinSize(minWidth = screenWidth - paddings.calculateLeftPadding(LayoutDirection.Ltr))
//
//                        ,
//                        onDraw = {
//
//                            draw(scope = this, textMeasurer, textStyle)
//
//                            drawLine(
//                                Color.White,
//                                Offset(horizontalSpacer, verticalSpacer),
//                                Offset(horizontalSpacer, size.height - 50),
//                                strokeWidth = 4f
//                            )
//                            drawLine(
//                                Color.White,
//                                Offset(horizontalSpacer, verticalSpacer),
//                                Offset(500f, verticalSpacer),
//                                strokeWidth = 4f
//                            )
//                            drawLine(
//                                Color.White,
//                                Offset(horizontalSpacer, size.height - 50),
//                                Offset(500f, size.height - 50),
//                                strokeWidth = 4f
//                            )
//                        }
//                    )
                }



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
                    PlaceExerciseButton(
                        viewState.selectedExercise == TrainingType.PUSH_UP,
                        TrainingType.PUSH_UP
                    ) { viewModel.processIntent(StatisticsIntent.SelectPushUpsHistory) }
                    PlaceExerciseButton(
                        viewState.selectedExercise == TrainingType.PLANK,
                        TrainingType.PLANK
                    ) { viewModel.processIntent(StatisticsIntent.SelectPlankHistory) }
                    PlaceExerciseButton(
                        viewState.selectedExercise == TrainingType.CRUNCH,
                        TrainingType.CRUNCH
                    ) { viewModel.processIntent(StatisticsIntent.SelectCrunchHistory) }
                    PlaceExerciseButton(
                        viewState.selectedExercise == TrainingType.SQUATS,
                        TrainingType.SQUATS
                    ) { viewModel.processIntent(StatisticsIntent.SelectSquatsHistory) }
                    PlaceExerciseButton(
                        viewState.selectedExercise == TrainingType.RUNNING,
                        TrainingType.RUNNING
                    ) { viewModel.processIntent(StatisticsIntent.SelectRunningHistory) }

                }

                val yStep = 50
                val points = listOf(150f, 125f, 100f, 250f, 200f, 330f, 300f, 90f, 120f, 285f)
                TrainGraph(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 20.dp)
                        .height(240.dp)
                        .width(900.dp),
                    xValues = (0..9).map { it + 1 },
                    yValues = (0..6).map { (it + 1) * yStep },
                    points = points,
                    paddingSpace = 40.dp,
                    verticalStep = yStep,
                    gridColor = Color.White,
                    lineColor = MaterialTheme.colorScheme.surface,
                    pointColor = MaterialTheme.colorScheme.surface,
                )

//                Canvas(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 20.dp)
//                        .height(300.dp), onDraw = {
//                        drawLine(
//                            Color.White,
//                            Offset.Zero,
//                            Offset(0f, size.height),
//                            strokeWidth = 10f
//                        )
//
//                    }
//                )

            }

        }
    }
}

@Composable
fun PlaceExerciseButton(isSelected: Boolean, type: TrainingType, click: () -> Unit) {

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


@OptIn(ExperimentalTextApi::class)
fun draw(scope: DrawScope, textMeasurer: TextMeasurer, textStyle: TextStyle) {
    scope.apply {
        drawText(
            textMeasurer,
            "123",//viewState.weightHistory!!.last().weight.toString(),
            topLeft = Offset(0f, 0f),
            style = textStyle
        )
        drawText(
            textMeasurer,
            "0",
            style = textStyle,
            topLeft = Offset(30f, size.height - 50)
        )
    }
}
