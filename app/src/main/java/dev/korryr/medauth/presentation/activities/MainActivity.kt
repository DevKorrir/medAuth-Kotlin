package dev.korryr.medauth.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.korryr.medauth.navigation.AppNavigation
import dev.korryr.medauth.presentation.ui.theme.MedAuthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            // Theme state management
            var isDarkTheme by remember { mutableStateOf(false) }

            MedAuthTheme(
                darkTheme = isDarkTheme,
                dynamicColor = true
            ) {
                AppNavigation(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkTheme = !isDarkTheme },
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
        }
    }
}
