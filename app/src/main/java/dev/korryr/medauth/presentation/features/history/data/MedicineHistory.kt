package dev.korryr.medauth.presentation.features.history.data

data class MedicineHistory(
    val id: String,
    val medicineName: String,
    val batchNumber: String,
    val scanDate: String,
    val scanTime: String,
    val verificationStatus: VerificationStatus,
    val manufacturer: String,
    val expiryDate: String,
    val imageUrl: String = "",
    val location: String = "Kisumu, Kenya",
    val confidence: Float = 0.95f
)