package org.sn.notebykmp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

// Material 3 color schemes
private val darkColorScheme = darkColorScheme(
    primary = coralDarkPrimary,
    onPrimary = coralDarkOnPrimary,
    primaryContainer = coralDarkPrimaryContainer,
    onPrimaryContainer = coralDarkOnPrimaryContainer,
    inversePrimary = coralDarkInversePrimary,
    secondary = coralDarkSecondary,
    onSecondary = coralDarkOnSecondary,
    secondaryContainer = coralDarkSecondaryContainer,
    onSecondaryContainer = coralDarkOnSecondaryContainer,
    tertiary = coralDarkTertiary,
    onTertiary = coralDarkOnTertiary,
    tertiaryContainer = coralDarkTertiaryContainer,
    onTertiaryContainer = coralDarkOnTertiaryContainer,
    error = coralDarkError,
    onError = coralDarkOnError,
    errorContainer = coralDarkErrorContainer,
    onErrorContainer = coralDarkOnErrorContainer,
    background = coralDarkBackground,
    onBackground = coralDarkOnBackground,
    surface = coralDarkSurface,
    onSurface = coralDarkOnSurface,
    inverseSurface = coralDarkInverseSurface,
    inverseOnSurface = coralDarkInverseOnSurface,
    surfaceVariant = coralDarkSurfaceVariant,
    onSurfaceVariant = coralDarkOnSurfaceVariant,
    outline = coralDarkOutline
)

private val lightColorScheme = lightColorScheme(
    primary = coralLightPrimary,
    onPrimary = coralLightOnPrimary,
    primaryContainer = coralLightPrimaryContainer,
    onPrimaryContainer = coralLightOnPrimaryContainer,
    inversePrimary = coralLightInversePrimary,
    secondary = coralLightSecondary,
    onSecondary = coralLightOnSecondary,
    secondaryContainer = coralLightSecondaryContainer,
    onSecondaryContainer = coralLightOnSecondaryContainer,
    tertiary = coralLightTertiary,
    onTertiary = coralLightOnTertiary,
    tertiaryContainer = coralLightTertiaryContainer,
    onTertiaryContainer = coralLightOnTertiaryContainer,
    error = coralLightError,
    onError = coralLightOnError,
    errorContainer = coralLightErrorContainer,
    onErrorContainer = coralLightOnErrorContainer,
    background = coralLightBackground,
    onBackground = coralLightOnBackground,
    surface = coralLightSurface,
    onSurface = coralLightOnSurface,
    inverseSurface = coralLightInverseSurface,
    inverseOnSurface = coralLightInverseOnSurface,
    surfaceVariant = coralLightSurfaceVariant,
    onSurfaceVariant = coralLightOnSurfaceVariant,
    outline = coralLightOutline
)

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
internal fun AppTheme(
    systemIsDark: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)
        MaterialTheme(
            colorScheme = if (isDark) darkColorScheme else lightColorScheme,
            content = { Surface(content = content) }
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
