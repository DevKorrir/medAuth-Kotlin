package dev.korryr.medauth.presentation.features.scan

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class ScanState {
    object Idle : ScanState()
    object Scanning : ScanState()
    data class Success(val scannedCode: String) : ScanState()
    data class Error(val message: String) : ScanState()
}

@HiltViewModel
class ScanViewModel @Inject constructor() : ViewModel() {

    private val _scanState = MutableStateFlow<ScanState>(ScanState.Idle)
    val scanState: StateFlow<ScanState> = _scanState.asStateFlow()

    fun onBarcodeDetected(code: String) {
        // Debounce or filter repeated codes if necessary
        if (_scanState.value !is ScanState.Success) {
            _scanState.value = ScanState.Success(code)
        }
    }

    fun resetScan() {
        _scanState.value = ScanState.Idle
    }
    
    fun setScanning() {
        _scanState.value = ScanState.Scanning
    }
}
