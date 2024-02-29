package com.example.notebook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.notebook.WindowSize
import com.example.notebook.WindowSizeClass

private val DarkColorPalette = darkColors(
    primary =  Color.White,
    background = DarkGray,
    onBackground = Color.White,
    surface = LightBlue,
    onSurface = DarkGray
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun NoteBookTheme(
    windowSizeClass: WindowSizeClass,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    //first check orientation
    val orientation = when {
        windowSizeClass.width.size > windowSizeClass.height.size -> Orientation.LandScape
        else -> Orientation.Portrait
    }


    val sizeThatMatters = when(orientation){

        Orientation.Portrait -> windowSizeClass.width
        else -> windowSizeClass.height
    }

    val dimensions = when(sizeThatMatters){
        is WindowSize.Small -> smallDimensions
        is WindowSize.Compact -> compactDimensions
        is WindowSize.Medium -> mediumDimensions
        else -> largeDimensions
    }

    val typography = when(sizeThatMatters){
        is WindowSize.Small -> typographySmall
        is WindowSize.Compact -> typographyCompact
        is WindowSize.Medium -> typographyMedium
        else -> typographyBig
    }


    ProvideAppUtils(dimensions = dimensions, orientation =orientation   ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = Shapes,
            content = content
        )
    }
}

object  AppTheme {
    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current

    val orientation:Orientation
        @Composable
        get() = LocalOrientationMode.current
}