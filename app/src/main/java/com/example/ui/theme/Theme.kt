package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  lightColorScheme(
    primary = MusicAccentPurple,
    onPrimary = Color.White,
    secondary = MusicAccentGold,
    onSecondary = Color.White,
    tertiary = MusicGreen,
    background = MusicDarkBg,
    onBackground = OnMusicDarkBg,
    surface = MusicSurface,
    onSurface = OnMusicDarkBg,
    surfaceVariant = MusicSurfaceVariant,
    onSurfaceVariant = OnMusicMuted
  )

private val LightColorScheme =
  lightColorScheme(
    primary = MusicAccentPurple,
    onPrimary = Color.White,
    secondary = MusicAccentGold,
    onSecondary = Color.White,
    tertiary = MusicGreen,
    background = MusicDarkBg,
    onBackground = OnMusicDarkBg,
    surface = MusicSurface,
    onSurface = OnMusicDarkBg,
    surfaceVariant = MusicSurfaceVariant,
    onSurfaceVariant = OnMusicMuted
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false, // Default to false for light Professional Polish theme
  dynamicColor: Boolean = false, // Set to false to preserve our custom premium branding
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
