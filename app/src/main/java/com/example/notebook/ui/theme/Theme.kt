package com.example.notebook.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.notebook.WindowSize
import com.example.notebook.WindowSizeClass


private val DarkColorPalette = darkColorScheme(
    primary =  Color.White,
    background = DarkGray,
//    onBackground = Color.White,
    surface = DarkGray,
)

private val LightColorPalette = lightColorScheme(
    primary = Primary,
//    primaryVariant = Purple700,
    secondary = Secondary,
    surface = Color.White
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

//    val typography = when(sizeThatMatters){
//        is WindowSize.Small -> typographySmall
//        is WindowSize.Compact -> typographyCompact
//        is WindowSize.Medium -> typographyMedium
//        else -> typographyBig
//    }

        val typography = when(sizeThatMatters){
        is WindowSize.Small -> typography
        is WindowSize.Compact -> typography
        is WindowSize.Medium -> typography
        else -> typography
    }


    ProvideAppUtils(dimensions = dimensions, orientation =orientation   ) {
        MaterialTheme(
            colorScheme = colors,
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