package com.mobile.giku.viewmodel.state

sealed class UIState {
    data object Idle: UIState()
    data object Loading: UIState()
    data object Success: UIState()
    data class Error(val message: String): UIState()
}