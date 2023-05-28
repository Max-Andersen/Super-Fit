package com.example.superfitcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.usecases.SharedPreferencesInteractor
import com.example.superfitcompose.ui.Routes
import com.example.superfitcompose.ui.auth.code.EnterCodeScreen
import com.example.superfitcompose.ui.auth.login.LoginScreen
import com.example.superfitcompose.ui.auth.register.RegisterScreen
import com.example.superfitcompose.ui.exercise.ExerciseScreen
import com.example.superfitcompose.ui.imagelist.ImageListScreen
import com.example.superfitcompose.ui.main.exercises.AllExercisesScreen
import com.example.superfitcompose.ui.main.mainscreen.MainScreen
import com.example.superfitcompose.ui.mybody.MyBodyScreen
import com.example.superfitcompose.ui.theme.SuperFitComposeTheme
import org.koin.androidx.compose.get
import org.koin.androidx.compose.inject


val bottomPadding = 25.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)


        super.onCreate(savedInstanceState)

        setContent {
            SuperFitComposeTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.LAUNCH) {
                    composable(Routes.LAUNCH) { LaunchScreen(navController = navController)}
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
                    composable(Routes.MY_BODY_DETAILS) { MyBodyScreen(navController = navController) }
                    composable(Routes.IMAGE_LIST) { ImageListScreen(navController = navController) }
                    composable(Routes.TRAIN_PROGRESS){ }
                    composable(Routes.STATISTICS){ }

                }

            }
        }
    }
}


@Composable
fun LaunchScreen(navController: NavController) {

    LaunchedEffect(true) {
        navController.navigate(Routes.MAIN_SCREEN)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        //val sharedPrefs: SharedPreferencesInteractor by inject()
        //Log.d("!!!!", sharedPrefs.getAccessToken())
        //if (sharedPrefs.getAccessToken() != "") { // Todo(normal logic)
        //navController.navigate(Routes.LOGIN)
//    } else {
//        navController.navigate(Routes.LOGIN)
//    }
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