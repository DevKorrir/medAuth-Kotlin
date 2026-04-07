package dev.korryr.medauth.data.repository

import dev.korryr.medauth.core.ui.components.ResultStatus
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

data class VerificationResult(
    val status: ResultStatus,
    val confidence: Int,
    val explanation: String,
    val recommendedAction: String,
    val drugName: String? = null,
    val manufacturer: String? = null
)

@Singleton
class VerificationRepository @Inject constructor() {
    
    // Fake backend simulation
    suspend fun verifyCode(scannedCode: String): Result<VerificationResult> {
        delay(1500) // Simulate network delay
        
        return try {
            val result = when {
                scannedCode.length % 2 == 0 -> {
                    // Even length barcode -> VERIFIED
                    VerificationResult(
                        status = ResultStatus.VERIFIED,
                        confidence = 98,
                        explanation = "This product matches official manufacturer records and supply chain checkpoints securely.",
                        recommendedAction = "Safe to consume.",
                        drugName = "Amoxicillin 500mg",
                        manufacturer = "PharmaCorp Ltd."
                    )
                }
                scannedCode.startsWith("C", ignoreCase = true) -> {
                    // Starts with C -> INVALID/COUNTERFEIT
                    VerificationResult(
                        status = ResultStatus.INVALID,
                        confidence = 92,
                        explanation = "Serialization codes are invalid or completely missing from the global registry.",
                        recommendedAction = "DO NOT CONSUME. Report this product immediately.",
                        drugName = "Unknown Compound",
                        manufacturer = "Unverified Source"
                    )
                }
                scannedCode.length < 5 -> {
                    VerificationResult(
                        status = ResultStatus.UNKNOWN,
                        confidence = 0,
                        explanation = "The barcode could not be recognized as a valid medical serialization format.",
                        recommendedAction = "Try rescanning or checking the label manually.",
                        drugName = null,
                        manufacturer = null
                    )
                }
                else -> {
                    // Otherwise -> SUSPICIOUS
                    VerificationResult(
                        status = ResultStatus.SUSPICIOUS,
                        confidence = 65,
                        explanation = "Serialization code exists, but it has been scanned in multiple disparate locations within a short time.",
                        recommendedAction = "Contact your pharmacist to double check the batch number.",
                        drugName = "Ibuprofen 400mg",
                        manufacturer = "GenericHealth Inc."
                    )
                }
            }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
