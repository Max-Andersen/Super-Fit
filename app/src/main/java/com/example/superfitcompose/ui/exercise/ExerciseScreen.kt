package com.example.superfitcompose.ui.exercise

import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.superfitcompose.R
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.ui.theme.SuperFitComposeTheme
import com.vmadalin.easypermissions.EasyPermissions
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

internal var lastUpdate = 0L

internal var valueY = 0f

internal var valueZ = 0f

internal var movementDown = false
internal var movementUp = false

@Composable
fun ShowLaunchedWarning(
    exerciseType: TrainingType,
    beginValue: Int,
    sendIntent: (ExerciseIntent) -> Unit,
    navigateUp: () -> Unit
) {
    Dialog(
        onDismissRequest = {},
    ) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.error
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 15.dp, top = 15.dp)
                ) {

                    val text = when (exerciseType) {
                        TrainingType.PLANK -> R.string.start_exercise_warning_seconds
                        TrainingType.RUNNING -> R.string.start_exercise_warning_meters
                        else -> R.string.start_exercise_warning_times
                    }

                    Text(
                        text = stringResource(id = R.string.will_we_start_train),
                        style = MaterialTheme.typography.headlineSmall,
                    )

                    Text(
                        text = stringResource(id = text).format(beginValue),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = stringResource(id = R.string.later),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.clickable { navigateUp() }
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(
                            text = stringResource(id = R.string.go),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.clickable { sendIntent(ExerciseIntent.StartExercise) }
                        )
                    }

                }
            }
        }

    }
}

internal var sensitivity = 1.5f

internal var sensorDelay = 500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    navController: NavController,
    exerciseType: TrainingType,
    viewModel: ExerciseViewModel = koinViewModel()
) {

    PrepareSensors(exerciseType, viewModel::processIntent)

    val screenState by viewModel.getScreenState().observeAsState(ExerciseViewState())

    if (screenState.launchedMessage) {
        ShowLaunchedWarning(
            exerciseType,
            screenState.beginCounterValue,
            viewModel::processIntent
        ) { navController.navigateUp() }
    }


    SuperFitComposeTheme {
        val snackbarHostState = remember {
            SnackbarHostState()
        }

        Scaffold(snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }, containerColor = MaterialTheme.colorScheme.secondary) { paddings ->

            val text = screenState.error?.let { stringResource(id = it) } ?: ""

            LaunchedEffect(key1 = screenState.error) {
                if (screenState.error != null) {
                    snackbarHostState.showSnackbar(
                        text,
                        "Close",
                        duration = SnackbarDuration.Long
                    )
                    viewModel.processIntent(ExerciseIntent.ErrorProceed)
                }
            }

            Box(
                modifier = Modifier
                    .padding(paddings)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier) {
                    Text(
                        text = with(Locale.ROOT) {
                            exerciseType.name.lowercase(this)
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(this) else it.toString() }
                        },
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 56.dp)
                    )
                    ExerciseCounter(
                        screenState.counter,
                        if (screenState.beginCounterValue == 0) 1 else screenState.beginCounterValue,
                        screenState.finished,
                        exerciseType
                    )

                    Controllers(screenState.pause,
                        screenState.finished,
                        onPause = { viewModel.processIntent(ExerciseIntent.PauseExercise) },
                        onStart = { viewModel.processIntent(ExerciseIntent.StartExercise) },
                        onStop = { viewModel.processIntent(ExerciseIntent.FinishExercise) })
                }
            }

        }


    }
}

@Composable
fun PrepareSensors(exerciseType: TrainingType, sendIntent: (ExerciseIntent) -> Unit) {
    val isActivityRecognitionPermissionFree = false
    val isActivityRecognitionPermissionGranted = EasyPermissions.hasPermissions(
        LocalContext.current, ACTIVITY_RECOGNITION
    )

    if (isActivityRecognitionPermissionFree || isActivityRecognitionPermissionGranted) {
    } else {
        EasyPermissions.requestPermissions(
            host = LocalContext.current as Activity,
            rationale = "For showing your step counts and calculate the average pace.",
            requestCode = 1,//REQUEST_CODE_ACTIVITY_RECOGNITION
            perms = arrayOf(ACTIVITY_RECOGNITION)
        )
    }

    val sensorManager =
        LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager


    var previousTotalSteps = 0f

    val movementListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val curTime = System.currentTimeMillis()

            if (curTime - lastUpdate > sensorDelay) {
                lastUpdate = curTime
                processSensorMovement(event, exerciseType) {
                    sendIntent(
                        ExerciseIntent.ExerciseStepDone()
                    )
                }

            }
        }

        override fun onAccuracyChanged(p0: Sensor, p1: Int) {}
    }

    val stepCountListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val totalSteps = event.values[0]
            if (previousTotalSteps == 0f) previousTotalSteps = totalSteps

            val currentSteps = totalSteps.toInt() - previousTotalSteps
            previousTotalSteps = totalSteps

            sendIntent(
                ExerciseIntent.ExerciseStepDone(currentSteps.toInt())
            )
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    }

    LaunchedEffect(key1 = true) {
        sendIntent(ExerciseIntent.LoadExerciseData(exerciseType))

        when (exerciseType) {
            TrainingType.PLANK -> {
                // View Model will start timer for plank
            }

            TrainingType.CRUNCH -> {

            }

            TrainingType.RUNNING -> {
                val sensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
                sensorManager.registerListener(
                    stepCountListener, sensor, SensorManager.SENSOR_DELAY_NORMAL
                )
            }

            else -> { // Push-Ups or Squats, both need ACCELERATION
                val sensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
                sensorManager.registerListener(
                    movementListener, sensor, SensorManager.SENSOR_DELAY_FASTEST
                )
            }
        }
    }


    DisposableEffect(key1 = true) {
        onDispose {
            sensorManager.unregisterListener(movementListener)
            sensorManager.unregisterListener(stepCountListener)
        }
    }
}

fun processSensorMovement(
    event: SensorEvent, exerciseType: TrainingType, processIntent: () -> Unit,
) {
    valueY = event.values[1]
    valueZ = event.values[2]

    if (exerciseType == TrainingType.SQUATS) {
        if (valueY < -sensitivity) {
            movementDown = true
        }

        if (valueY > sensitivity) {
            movementUp = true
        }
        //Toast.makeText(context, "$movementDown  $movementUp", Toast.LENGTH_SHORT).show()

    }

    if (exerciseType == TrainingType.PUSH_UP) {
        Log.d("SENSOR", "$movementDown  $movementUp")
        if (valueZ < -sensitivity) {
            movementDown = true
        }

        if (valueZ > sensitivity) {
            movementUp = true
        }
        //Toast.makeText(context, "$movementDown  $movementUp", Toast.LENGTH_SHORT).show()

    }


    if (movementDown && movementUp) {
        processIntent.invoke()
        movementDown = false
        movementUp = false
    }
}

@Composable
fun ExerciseCounter(
    counter: Int, counterBeginValue: Int, finished: Boolean, exerciseType: TrainingType
) {

    val progressAngle by animateFloatAsState(
        targetValue = 360f / counterBeginValue.toFloat() * counter, animationSpec = tween(500)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 72.dp, end = 72.dp, top = 64.dp)
            .aspectRatio(1f)
    ) {
        ExerciseProgress(counter, finished, exerciseType)
        CircleProgress(angle = progressAngle)
    }
}

@Composable
fun ExerciseProgress(
    counter: Int,
    finished: Boolean,
    exerciseType: TrainingType,
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

        val signText = when (exerciseType) {
            TrainingType.SQUATS -> R.string.exercise_times_left
            TrainingType.PUSH_UP -> R.string.exercise_times_left
            TrainingType.PLANK -> R.string.exercise_seconds_left
            TrainingType.CRUNCH -> R.string.exercise_need_to_do
            TrainingType.RUNNING -> R.string.exercise_meters_to_pass
        }

        if (finished) {
            if (counter <= 0 || exerciseType == TrainingType.CRUNCH) {
                Image(
                    painter = painterResource(id = R.drawable.completed_tick),
                    contentDescription = null,
                )
            } else {
                val str = String.format(
                    stringResource(id = R.string.exercise_not_finished),
                    counter.toString()
                )
                Text(
                    text = str,
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
                text = stringResource(id = signText),
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
    Box(modifier = Modifier
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
        })
}


@Preview
@Composable
fun ExerciseScreenPreview() {
    ExerciseScreen(navController = rememberNavController(), exerciseType = TrainingType.SQUATS)
}
