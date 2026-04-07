package dev.korryr.medauth.presentation.features.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ReportState {
    object Idle : ReportState()
    object Submitting : ReportState()
    object Success : ReportState()
    data class Error(val message: String) : ReportState()
}

@HiltViewModel
class ReportViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<ReportState>(ReportState.Idle)
    val uiState: StateFlow<ReportState> = _uiState.asStateFlow()

    private val _notes = MutableStateFlow("")
    val notes: StateFlow<String> = _notes.asStateFlow()

    fun updateNotes(newNotes: String) {
        _notes.value = newNotes
    }

    fun submitReport() {
        _uiState.value = ReportState.Submitting
        viewModelScope.launch {
            try {
                // Simulating network delay and submission
                delay(2000)
                _uiState.value = ReportState.Success
            } catch (e: Exception) {
                _uiState.value = ReportState.Error(e.message ?: "Failed to submit report")
            }
        }
    }

    fun resetState() {
        _uiState.value = ReportState.Idle
        _notes.value = ""
    }
}
