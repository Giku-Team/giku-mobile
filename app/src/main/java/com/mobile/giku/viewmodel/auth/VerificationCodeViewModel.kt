package com.mobile.giku.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.repository.auth.AuthRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.launch

class VerificationCodeViewModel(private val repository: AuthRepository): ViewModel() {

    private var _verificationCodeState = MutableLiveData<UIState>()
    val verificationCodeState: LiveData<UIState> = _verificationCodeState

    fun verificationCode(email: String, verficationCode: String) {
        _verificationCodeState.value = UIState.Loading
        viewModelScope.launch {
            val result = repository.validateResetCode(email, verficationCode)
            result.onSuccess {
                _verificationCodeState.value = UIState.Success
            }.onFailure {
                _verificationCodeState.value = UIState.Error(it.message ?: "Process failed")
            }
        }
    }
}