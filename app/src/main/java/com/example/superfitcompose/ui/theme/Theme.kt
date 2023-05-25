package com.example.superfitcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val ColorScheme = lightColorScheme(
    primary = LightPurple,
    onPrimary = White,
    secondary = Background,
    onSecondary = OnBackground,
    surface = Surface,
    error = Alert,
    onError = Purple2,
    errorContainer = DialogHint

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

fun ColorScheme.background() = Background

@Composable
fun SuperFitComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = ColorScheme

//    val currentActivity = LocalView.current.context as Activity         // Not working with Preview
//    currentActivity.window.statusBarColor = Color.Transparent.toArgb()

//    WindowInsetsControllerCompat(currentActivity.window, LocalView.current).let { controller ->
//        controller.systemBarsBehavior =
//            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        controller.hide(WindowInsetsCompat.Type.navigationBars())
//    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.Transparent
    )
    systemUiController.setNavigationBarColor(
        color = Color.Transparent

    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MyTypography,
        content = content
    )
}