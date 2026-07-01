package com.example.babysleepsounds.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val BedtimeColors = darkColorScheme(
    primary = SoftLavender,
    onPrimary = Color.White,
    primaryContainer = CardNight,
    onPrimaryContainer = TextWhite,
    secondary = LightPurple,
    onSecondary = Color.White,
    tertiary = SoftYellowMoon,
    background = DeepMidnightBlue,
    onBackground = TextWhite,
    surface = CardNight,
    onSurface = TextWhite,
    surfaceVariant = MidnightBlue,
    onSurfaceVariant = TextLightGray,
    outline = LightPurple.copy(alpha = 0.45f)
)

@Composable
fun BabySleepSoundsTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = DeepMidnightBlue.toArgb()
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = BedtimeColors,
        typography = BabySleepTypography,
        shapes = BabySleepShapes,
        content = content
    )
}
