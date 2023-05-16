package com.example.superfitcompose

import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.ui.Routes
import com.example.superfitcompose.ui.auth.code.EnterCodeScreen
import com.example.superfitcompose.ui.auth.login.LoginScreen
import com.example.superfitcompose.ui.auth.register.RegisterScreen
import com.example.superfitcompose.ui.exercise.ExerciseScreen
import com.example.superfitcompose.ui.main.exercises.AllExercisesScreen
import com.example.superfitcompose.ui.main.mainscreen.MainScreen
import com.example.superfitcompose.ui.theme.SuperFitComposeTheme


val bottomPadding = 25.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)


//        val defaultDisplay = getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
//
//
//        val metrics = DisplayMetrics()
//        windowManager.defaultDisplay.getRealMetrics(metrics)
//        val decorViewHeight = window.decorView.height
//        val screenHeight = metrics.heightPixels
//        val navigationBarHeight = decorViewHeight - screenHeight




        super.onCreate(savedInstanceState)
        setContent {
            SuperFitComposeTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.LOGIN) {
                    composable(Routes.LOGIN) { LoginScreen(navController = navController) }
                    composable(Routes.ENTER_PASSWORD + "/{email}") { navBackStack ->
                        val enteredEmail = navBackStack.arguments?.getString("email")!!
                        EnterCodeScreen(email = enteredEmail, navController = navController)
                    }
                    composable(Routes.REGISTER) { RegisterScreen(navController = navController) }
                    composable(Routes.MAIN_SCREEN) { MainScreen(navController = navController) }
                    composable(Routes.ALL_EXERCISES) { AllExercisesScreen(navController = navController) }
                    composable(Routes.EXERCISE + "/{exercise_type}") { navBackStack ->
                        val exercise =
                            TrainingType.valueOf(navBackStack.arguments?.getString("exercise_type")!!)
                        ExerciseScreen(
                            navController = navController,
                            exerciseType = exercise
                        )
                    }
                }

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