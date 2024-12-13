package com.mobile.giku.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.model.datastore.AuthDataStore
import com.mobile.giku.repository.AuthRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _loginState = MutableLiveData<UIState>().apply { value = UIState.Idle }
    val loginState: LiveData<UIState> = _loginState

    val isLoggedIn: LiveData<Boolean> = authDataStore.getLoginStatus()

    fun login(email: String, password: String) {
        _loginState.value = UIState.Loading
        viewModelScope.launch {
            repository.login(email, password)
                .onSuccess { response ->
                    _loginState.value = UIState.Success
                    authDataStore.setLoginStatus(true)
                    authDataStore.saveToken(response.token)
                }
                .onFailure { throwable ->
                    _loginState.value = UIState.Error(throwable.message ?: "Unknown error occurred")
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authDataStore.clearAuthData()
            _loginState.value = UIState.Idle
        }
    }
}
