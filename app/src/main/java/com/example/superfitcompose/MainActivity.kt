package com.example.superfitcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                        ExerciseScreen(navController = navController, exerciseType = TrainingType.CRUNCH)
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