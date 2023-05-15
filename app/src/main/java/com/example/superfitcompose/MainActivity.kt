package com.example.superfitcompose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.ui.Routes
import com.example.superfitcompose.ui.auth.code.EnterCodeScreen
import com.example.superfitcompose.ui.auth.login.LoginScreen
import com.example.superfitcompose.ui.auth.register.RegisterScreen
import com.example.superfitcompose.ui.exercise.ExerciseScreen
import com.example.superfitcompose.ui.theme.SuperFitComposeTheme
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperFitComposeTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.EXERCISE) {
                    composable(Routes.LOGIN) { LoginScreen(navController = navController) }
                    composable(Routes.ENTER_PASSWORD + "/{email}") { navBackStack ->
                        val enteredEmail = navBackStack.arguments?.getString("email")!!
                        EnterCodeScreen(email = enteredEmail, navController = navController)
                    }
                    composable(Routes.REGISTER) { RegisterScreen(navController = navController) }
                    composable(Routes.EXERCISE) { navBackStack ->
//                        val exercise =
//                            TrainingType.valueOf(navBackStack.arguments?.getString("exercise_type")!!)
                        ExerciseScreen(navController = navController, exerciseType = TrainingType.RUNNING)
                    }
                    composable("test") { TestAccelerometr() }
                }

            }
        }
    }
}


@Composable
fun TestAccelerometr() {
    val sensorManager =
        LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    var lastUpdate = 0L

    var deltaY = remember {
        mutableStateOf(0f)
    }

    var deltaZ = remember {
        mutableStateOf(0f)
    }

    var squatDown = false
    var squatUp = false


    val sensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            val curTime = System.currentTimeMillis()

            if (curTime - lastUpdate > 500) {
                lastUpdate = curTime

                deltaY.value = event.values[1]
                deltaZ.value = event.values[2]


                if (abs(deltaZ.value) < 2)
                    deltaZ.value = 0f
                if (abs(deltaY.value) < 2)
                    deltaY.value = 0f


                if (deltaY.value < -2f) {
                    squatDown = true
                }

                if (deltaY.value > 2f) {
                    squatUp = true
                }
            }

            //TODO("Not yet implemented")
        }

        override fun onAccuracyChanged(p0: Sensor, p1: Int) {
            //TODO("Not yet implemented")
        }

    }

    sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)

    SuperFitComposeTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text(text = "Last X")
//            Text(text = lastX.value.toString())
//            Text(text = "Last Y")
//            Text(text = lastY.value.toString())
//            Text(text = "Last Z")
//            Text(text = lastZ.value.toString())
            Text(text = "Delta Y")
            Text(text = if (deltaY.value != 0f) deltaY.value.toString() else "-")
            Text(text = "Delta Z")
            Text(text = if (deltaZ.value != 0f) deltaZ.value.toString() else "-")

            if (squatDown && squatUp) {
                Text(text = "SQUAT!!!")
                squatDown = false
                squatUp = false
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SuperFitComposeTheme {
        Greeting("Android")
    }
}