package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DzimbabweColorScheme = darkColorScheme(
    primary = PoolGold,
    onPrimary = PoolDarkBg,
    primaryContainer = PoolGoldDark,
    onPrimaryContainer = PoolTextPrimary,
    secondary = PoolAqua,
    onSecondary = PoolDarkBg,
    secondaryContainer = PoolTeal,
    onSecondaryContainer = PoolTextPrimary,
    tertiary = PoolWater,
    onTertiary = PoolDarkBg,
    background = PoolDarkBg,
    onBackground = PoolTextPrimary,
    surface = PoolSurface,
    onSurface = PoolTextPrimary,
    surfaceVariant = PoolSurfaceElevated,
    onSurfaceVariant = PoolTextSecondary,
    outline = PoolBorder,
    outlineVariant = PoolBorderGold
)

@Composable
fun DzimbabwePoolsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                WindowCompat.setDecorFitsSystemWindows(it, false)
                it.statusBarColor = PoolDarkBg.toArgb()
                it.navigationBarColor = PoolDarkBg.toArgb()
                val controller = WindowCompat.getInsetsController(it, view)
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = DzimbabweColorScheme,
        typography = Typography,
        content = content
    )
}
