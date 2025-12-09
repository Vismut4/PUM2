package com.example.lista3z2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lista3z2.data.SerialNumberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class SerialUiState(
    val serial: String = "",
    val isLoading: Boolean = false
)

class SerialVerificationViewModel(
    private val repository: SerialNumberRepository = SerialNumberRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SerialUiState())
    val uiState: StateFlow<SerialUiState> = _uiState.asStateFlow()

    private val _validationEvent = MutableSharedFlow<Boolean>()
    val validationEvent: SharedFlow<Boolean> = _validationEvent.asSharedFlow()

    fun onSerialChanged(newSerial: String) {
        _uiState.value = _uiState.value.copy(serial = newSerial)
    }

    fun onVerifyClicked() {
        val serial = _uiState.value.serial
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = withContext(Dispatchers.Default) {
                repository.verifySerialNumber(serial)
            }
            _validationEvent.emit(result)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}


