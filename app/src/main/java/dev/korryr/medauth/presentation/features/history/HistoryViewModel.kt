package dev.korryr.medauth.presentation.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.korryr.medauth.data.local.database.ScanDao
import dev.korryr.medauth.data.local.database.ScanEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val scanDao: ScanDao
) : ViewModel() {

    val scanHistory: StateFlow<List<ScanEntity>> = scanDao.getAllScans()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun clearHistory() {
        viewModelScope.launch {
            scanDao.clearHistory()
        }
    }
}
