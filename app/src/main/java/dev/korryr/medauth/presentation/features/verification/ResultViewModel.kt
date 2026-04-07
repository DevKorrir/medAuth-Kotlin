package dev.korryr.medauth.presentation.features.verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.korryr.medauth.data.repository.VerificationRepository
import dev.korryr.medauth.data.repository.VerificationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ResultScreenState {
    object Loading : ResultScreenState()
    data class Success(val result: VerificationResult) : ResultScreenState()
    data class Error(val message: String) : ResultScreenState()
}

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: VerificationRepository
) : ViewModel() {

    private val scannedCode: String = checkNotNull(savedStateHandle["scanId"])

    private val _uiState = MutableStateFlow<ResultScreenState>(ResultScreenState.Loading)
    val uiState: StateFlow<ResultScreenState> = _uiState.asStateFlow()

    init {
        verifyCode()
    }

    fun verifyCode() {
        _uiState.value = ResultScreenState.Loading
        viewModelScope.launch {
            repository.verifyCode(scannedCode)
                .onSuccess { result ->
                    _uiState.value = ResultScreenState.Success(result)
                }
                .onFailure { exception ->
                    _uiState.value = ResultScreenState.Error(exception.message ?: "Verification failed.")
                }
        }
    }
}
