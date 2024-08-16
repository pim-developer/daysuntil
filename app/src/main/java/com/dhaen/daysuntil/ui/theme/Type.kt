package com.dhaen.daysuntil.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dhaen.daysuntil.R

// Choose your fonts for free from https://fonts.google.com/

val ShrikhandRegular = FontFamily(Font(R.font.shrikhand_regular))

val SometypeMonoVariable = FontFamily(Font(R.font.sometypemono_variablefont_wght))

val Caveat = FontFamily(Font(R.font.caveat_variablefont_wght))


// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = ShrikhandRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    displayMedium = TextStyle(
        fontFamily = ShrikhandRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    displaySmall = TextStyle(
        fontFamily = ShrikhandRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Caveat,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = SometypeMonoVariable,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = SometypeMonoVariable,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = SometypeMonoVariable,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp,
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