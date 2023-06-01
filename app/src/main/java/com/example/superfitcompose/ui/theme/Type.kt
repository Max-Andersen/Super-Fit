package com.example.superfitcompose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.superfitcompose.R


val myFontFamily = FontFamily(
    Font(resId = R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(resId = R.font.montserrat_light, weight = FontWeight.Light),
    Font(resId = R.font.montserrat_regular, weight = FontWeight.Normal)
)

val letterSpacing = 0.sp


// Set of Material typography styles to start with

//fontFamily = montserratBold,


val MyTypography = Typography(

    headlineLarge = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 64.sp,
        lineHeight = 78.sp,
        letterSpacing = letterSpacing,
        color = Color.White
    ),

    headlineMedium = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = letterSpacing,
        color = Color.White
    ),

    headlineSmall = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 29.sp,
        letterSpacing = letterSpacing,
        color = Color.White
    ),

    bodyMedium = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = letterSpacing,
        color = Color.White
    ),

    bodySmall = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 15.sp,
        letterSpacing = letterSpacing,
        color = OnBackground
    ),


    labelMedium = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 64.sp,
        lineHeight = 78.sp,
        letterSpacing = letterSpacing,
        color = Color.White
    ),

    labelSmall = TextStyle(
        fontSize = 16.sp,
        lineHeight = 10.sp,
        letterSpacing = letterSpacing,
        color = Color.White
    )



    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)