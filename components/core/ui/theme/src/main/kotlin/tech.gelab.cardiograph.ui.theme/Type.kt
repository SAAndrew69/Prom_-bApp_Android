package tech.gelab.cardiograph.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

val interFontFamily = FontFamily(
    Font(
        R.font.inter_regular,
        FontWeight.Normal
    ),
    Font(
        R.font.inter_black,
        FontWeight.Black
    ),
    Font(
        R.font.inter_bold,
        FontWeight.Bold
    ),
    Font(
        R.font.inter_extrabold,
        FontWeight.ExtraBold
    ),
    Font(
        R.font.inter_light,
        FontWeight.Light
    ),
    Font(
        R.font.inter_extralight,
        FontWeight.ExtraLight
    ),
    Font(
        R.font.inter_semibold,
        FontWeight.SemiBold
    ),
    Font(
        R.font.inter_thin,
        FontWeight.Thin
    ),
    Font(
        R.font.inter_medium,
        FontWeight.Medium
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
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
    bodyMedium = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = TextUnit(24f, TextUnitType.Sp),
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = TextUnit(20f, TextUnitType.Sp),
        fontSize = 12.sp
    ),
    labelMedium = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = TextUnit(20f, TextUnitType.Sp),
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = TextUnit(24f, TextUnitType.Sp),
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = TextUnit(24f, TextUnitType.Sp),
        fontSize = 24.sp
    ),
//    TODO
//    headlineSmall = TextStyle(
//        fontFamily = interFontFamily,
//        fontWeight = FontWeight.SemiBold,
//        lineHeight = TextUnit(24f, TextUnitType.Sp),
//        fontSize = 16.sp
//    ),
    headlineSmall = TextStyle(
        fontFamily = interFontFamily,
        fontWeight =  FontWeight.Medium,
        lineHeight = TextUnit(24f, TextUnitType.Sp),
        fontSize = 20.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.SemiBold,
        lineHeight = TextUnit(24f, TextUnitType.Sp),
        fontSize = 20.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp,
        fontSize = 32.sp
    )
)