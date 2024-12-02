package com.mobile.giku.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.repository.auth.AuthRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _registerState = MutableLiveData<UIState>()
    val registerState: LiveData<UIState> = _registerState

    fun register(email: String, password: String, name: String) {
        _registerState.value = UIState.Loading
        viewModelScope.launch {
            val result = repository.register(email, password, name)
            result.onSuccess {
                _registerState.value = UIState.Success
            }.onFailure {
                _registerState.value = UIState.Error(it.message ?: "Registration failed")
            }
        }
    }
}
