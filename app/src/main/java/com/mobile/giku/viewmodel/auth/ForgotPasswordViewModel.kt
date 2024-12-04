package com.mobile.giku.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.repository.auth.AuthRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val repository: AuthRepository): ViewModel() {

    private val _forgotPasswordState = MutableLiveData<UIState>()
    val forgotPasswordState: LiveData<UIState> = _forgotPasswordState

    fun forgotPassword(email: String) {
        _forgotPasswordState.value = UIState.Loading
        viewModelScope.launch {
            val result = repository.forgotPassword(email)
            result.onSuccess {
                _forgotPasswordState.value = UIState.Success
            }.onFailure {
                _forgotPasswordState.value = UIState.Error(it.message ?: "Process failed")
            }
        }
    }
}