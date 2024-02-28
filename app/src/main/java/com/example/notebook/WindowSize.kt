package com.example.notebook

import android.app.LocaleConfig
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

sealed class WindowSize(val size: Int){

    data class Small(val smallSize: Int): WindowSize(smallSize)
    data class Compact(val compactSize: Int): WindowSize(compactSize)
    data class Medium(val mediumSize: Int): WindowSize(mediumSize)
    data class Large(val largeSize: Int): WindowSize(largeSize)
}


data class WindowSizeClass(
    val width: WindowSize,
    val height: WindowSize
)

@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val config = LocalConfiguration.current

    val width by remember(config) {
            mutableStateOf(config.screenWidthDp)
    }

    val height by remember(config) {
        mutableStateOf(config.screenHeightDp)
    }



    val windowWidthClass  = when{
        width <= 360  -> WindowSize.Small(width)
        width in 361..400 -> WindowSize.Compact(width)
        width in 481..720 -> WindowSize.Medium(width)
        else -> WindowSize.Large(width)
    }



    val windowHeightClass  = when{
        height <= 360  -> WindowSize.Small(height)
        height in 361..400 -> WindowSize.Compact(height)
        height in 481..720 -> WindowSize.Medium(height)
        else -> WindowSize.Large(height)
    }

    return WindowSizeClass(windowWidthClass, windowHeightClass)
}