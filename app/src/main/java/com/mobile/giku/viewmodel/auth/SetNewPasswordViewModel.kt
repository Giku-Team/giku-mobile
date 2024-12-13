package com.mobile.giku.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.repository.AuthRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.launch

class SetNewPasswordViewModel(private val repository: AuthRepository): ViewModel() {

    private val _setNewPasswordState = MutableLiveData<UIState>()
    val setNewPasswordState: LiveData<UIState> = _setNewPasswordState

    fun setNewPassword(email: String, newPassword: String) {
        _setNewPasswordState.value = UIState.Loading
        viewModelScope.launch {
            val result = repository.resetPassword(email, newPassword)
            result.onSuccess {
                _setNewPasswordState.value = UIState.Success
            }.onFailure {
                _setNewPasswordState.value = UIState.Error(it.message ?: "Process failed")
            }
        }
    }
}