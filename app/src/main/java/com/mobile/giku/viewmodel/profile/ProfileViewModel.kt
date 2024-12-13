package com.mobile.giku.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.model.datastore.AuthDataStore
import com.mobile.giku.model.remote.user.UserData
import com.mobile.giku.repository.UserRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class ProfileViewModel(
    private val repository: UserRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId.asStateFlow()

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData.asStateFlow()


    private val _uiState = MutableLiveData<UIState>(UIState.Idle)
    val uiState: LiveData<UIState> get() = _uiState

    init {
        viewModelScope.launch {
            authDataStore.getUserId().collect { decodedUserId ->
                _userId.value = decodedUserId
            }
        }
    }

    fun fetchUser(userId: String) {
        _uiState.value = UIState.Loading

        viewModelScope.launch {
            try {
                val response = repository.getUser(userId)
                when {
                    response.isSuccessful && response.body() != null -> {
                        _userData.value = response.body()?.data
                        _uiState.value = UIState.Success
                    }
                    response.code() == 401 || response.code() == 403 -> {
                        _uiState.value = UIState.Error("Sesi anda telah berakhir. Silakan login kembali")
                    }
                    response.code() == 404 -> {
                        _uiState.postValue(
                            UIState.Error("Profil pengguna tidak ditemukan. Silakan periksa kembali")
                        )
                    }
                    response.code() == 500 -> {
                        _uiState.postValue(
                            UIState.Error("Terjadi kesalahan di server. Silakan coba lagi nanti")
                        )
                    }
                    else -> {
                        _uiState.postValue(
                            UIState.Error("Gagal memuat profil. Silakan coba lagi")
                        )
                    }
                }
            } catch (e: IOException) {
                _uiState.postValue(
                    UIState.Error("Tidak ada koneksi internet. Periksa jaringan anda")
                )
            } catch (e: SocketTimeoutException) {
                _uiState.postValue(
                    UIState.Error("Waktu tunggu habis. Periksa koneksi internet anda")
                )
            } catch (e: Exception) {
                _uiState.postValue(
                    UIState.Error("Terjadi kesalahan. Silakan coba lagi")
                )
            }
        }
    }
}