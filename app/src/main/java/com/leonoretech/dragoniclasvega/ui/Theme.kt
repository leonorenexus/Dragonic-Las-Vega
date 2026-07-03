package com.leonoretech.dragoniclasvegas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DragonicColorScheme = darkColorScheme(
    primary = NeonPurple,
    onPrimary = TextPrimary,
    secondary = NeonBlue,
    onSecondary = VoidBlack,
    tertiary = NeonGold,
    background = VoidBlack,
    onBackground = TextPrimary,
    surface = DeepPurple,
    onSurface = TextPrimary,
    error = DangerRed
)

@Composable
fun DragonicLasVegaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DragonicColorScheme,
        typography = DragonicTypography,
        content = content
    )
}
