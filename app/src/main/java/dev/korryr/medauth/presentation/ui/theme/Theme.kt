package dev.korryr.medauth.presentation.ui.theme

import android.app.Activity
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

private val DarkColorScheme = darkColorScheme(
    // Primary colors
    primary = MedicalBlueLight,
    onPrimary = MedicalBlueDark,
    primaryContainer = MedicalBlueDark,
    onPrimaryContainer = MedicalBlueLight,

    // Secondary colors
    secondary = HealthGreenLight,
    onSecondary = HealthGreenDark,
    secondaryContainer = HealthGreenDark,
    onSecondaryContainer = HealthGreenLight,

    // Tertiary colors
    tertiary = MedicalAmberLight,
    onTertiary = MedicalAmberDark,
    tertiaryContainer = MedicalAmberDark,
    onTertiaryContainer = MedicalAmberLight,

    // Error colors
    error = MedicalRedLight,
    onError = MedicalRedDark,
    errorContainer = MedicalRedDark,
    onErrorContainer = MedicalRedLight,

    // Background colors
    background = Color(0xFF0F0F0F),                // RGB(15, 15, 15) - Deep dark
    onBackground = Color(0xFFE8E8E8),              // RGB(232, 232, 232) - Light text

    // Surface colors
    surface = Color(0xFF1A1A1A),                   // RGB(26, 26, 26) - Card background
    onSurface = Color(0xFFE8E8E8),                 // RGB(232, 232, 232) - Text on cards
    surfaceVariant = Color(0xFF2D2D2D),            // RGB(45, 45, 45) - Elevated surfaces
    onSurfaceVariant = Color(0xFFB8B8B8),          // RGB(184, 184, 184) - Secondary text

    // Outline colors
    outline = Color(0xFF6B6B6B),                   // RGB(107, 107, 107) - Borders
    outlineVariant = Color(0xFF404040),            // RGB(64, 64, 64) - Subtle borders

    // Additional surface colors
    surfaceTint = MedicalBlueLight,
    inverseSurface = Color(0xFFE8E8E8),            // RGB(232, 232, 232)
    inverseOnSurface = Color(0xFF1A1A1A),          // RGB(26, 26, 26)
    inversePrimary = MedicalBlue,

    // Container colors
    surfaceContainer = Color(0xFF242424),          // RGB(36, 36, 36)
    surfaceContainerHigh = Color(0xFF2E2E2E),      // RGB(46, 46, 46)
    surfaceContainerHighest = Color(0xFF383838),   // RGB(56, 56, 56)
    surfaceContainerLow = Color(0xFF1F1F1F),       // RGB(31, 31, 31)
    surfaceContainerLowest = Color(0xFF141414),    // RGB(20, 20, 20)
)

private val LightColorScheme = lightColorScheme(
    // Primary colors
    primary = MedicalBlue,
    onPrimary = PureWhite,
    primaryContainer = MedicalBlueLight,
    onPrimaryContainer = MedicalBlueDark,

    // Secondary colors
    secondary = HealthGreen,
    onSecondary = PureWhite,
    secondaryContainer = HealthGreenLight,
    onSecondaryContainer = HealthGreenDark,

    // Tertiary colors (for accent elements)
    tertiary = MedicalAmber,
    onTertiary = PureWhite,
    tertiaryContainer = MedicalAmberLight,
    onTertiaryContainer = MedicalAmberDark,

    // Error colors
    error = MedicalRed,
    onError = PureWhite,
    errorContainer = MedicalRedLight,
    onErrorContainer = MedicalRedDark,

    // Background colors
    background = PureWhite,
    onBackground = CharcoalBlack,

    // Surface colors (for cards, dialogs, etc.)
    surface = PureWhite,
    onSurface = CharcoalBlack,
    surfaceVariant = CleanGray,
    onSurfaceVariant = DarkGray,

    // Outline colors (for borders, dividers)
    outline = MediumGray,
    outlineVariant = LightGray,

    // Additional surface colors
    surfaceTint = MedicalBlue,
    inverseSurface = CharcoalBlack,
    inverseOnSurface = PureWhite,
    inversePrimary = MedicalBlueLight,

    // Container colors
    surfaceContainer = CleanGray,
    surfaceContainerHigh = Color(0xFFECECEC),      // RGB(236, 236, 236)
    surfaceContainerHighest = Color(0xFFE6E6E6),   // RGB(230, 230, 230)
    surfaceContainerLow = Color(0xFFF8F8F8),       // RGB(248, 248, 248)
    surfaceContainerLowest = PureWhite,
)

@Composable
fun MedAuthTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}