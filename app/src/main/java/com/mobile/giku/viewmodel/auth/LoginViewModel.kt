package com.mobile.giku.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.model.datastore.AuthDataStore
import com.mobile.giku.repository.auth.AuthRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _loginState = MutableLiveData<UIState>()
    val loginState: LiveData<UIState> = _loginState

    fun login(email: String, password: String) {
        _loginState.value = UIState.Loading
        viewModelScope.launch {
            val result = repository.login(email, password)
            result.onSuccess {
                _loginState.value = UIState.Success
                authDataStore.setLoginStatus(true)
            }.onFailure {
                _loginState.value = UIState.Error(it.message ?: "Login failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authDataStore.clearLoginStatus()
        }
    }
}
