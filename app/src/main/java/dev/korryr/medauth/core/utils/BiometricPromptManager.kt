package dev.korryr.medauth.core.utils

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class BiometricResult {
    object HardwareUnavailable : BiometricResult()
    object FeatureUnavailable : BiometricResult()
    data class AuthenticationError(val error: String) : BiometricResult()
    object AuthenticationFailed : BiometricResult()
    object AuthenticationSuccess : BiometricResult()
    object AuthenticationNotSet : BiometricResult()
}

class BiometricPromptManager(
    private val context: Context
) {
    fun showBiometricPrompt(
        activity: FragmentActivity,
        title: String,
        description: String
    ): Flow<BiometricResult> = callbackFlow {
        
        val biometricManager = BiometricManager.from(context)
        val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL

        when (biometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                trySend(BiometricResult.HardwareUnavailable)
                close()
                return@callbackFlow
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                trySend(BiometricResult.FeatureUnavailable)
                close()
                return@callbackFlow
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                trySend(BiometricResult.AuthenticationNotSet)
                close()
                return@callbackFlow
            }
            else -> Unit // Ready to show prompt
        }

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators)
            .build()

        val biometricPrompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(context),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    trySend(BiometricResult.AuthenticationError(errString.toString()))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    trySend(BiometricResult.AuthenticationSuccess)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    trySend(BiometricResult.AuthenticationFailed)
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)

        awaitClose {
            biometricPrompt.cancelAuthentication()
        }
    }
}
