package com.mobile.giku.viewmodel.child

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.model.datastore.AuthDataStore
import com.mobile.giku.model.remote.child.AddChildRequest
import com.mobile.giku.model.remote.child.GetChildrenResponse
import com.mobile.giku.repository.ChildRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException

class ChildProfileViewModel(
    private val repository: ChildRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?> = _userId

    private val _addChildState = MutableLiveData<UIState>()
    val addChildState: LiveData<UIState> = _addChildState

    private val _getChildrenState = MutableLiveData<UIState>()
    val getChildrenState: LiveData<UIState> = _getChildrenState

    private val _childrenData = MutableLiveData<GetChildrenResponse?>()
    val childrenData: LiveData<GetChildrenResponse?> = _childrenData

    init {
        viewModelScope.launch {
            authDataStore.getUserId().collect { decodedUserId ->
                _userId.postValue(decodedUserId)
            }
        }
    }

    fun addChildProfile(addChildRequest: AddChildRequest) {
        viewModelScope.launch {
            _addChildState.postValue(UIState.Loading)
            try {
                val userId = authDataStore.getUserId().first()
                    ?: run {
                        _addChildState.postValue(
                            UIState.Error("Silakan login ulang untuk melanjutkan")
                        )
                        return@launch
                    }

                val response = repository.addChildProfile(userId, addChildRequest)
                when {
                    response.isSuccessful -> {
                        _addChildState.postValue(UIState.Success)
                    }
                    response.code() == 400 -> {
                        _addChildState.postValue(
                            UIState.Error("Pastikan semua data anak sudah terisi dengan benar")
                        )
                    }
                    response.code() == 401 || response.code() == 403 -> {
                        _addChildState.postValue(
                            UIState.Error("Sesi anda telah berakhir. Silakan login kembali")
                        )
                    }
                    response.code() == 408 -> {
                        _addChildState.postValue(
                            UIState.Error("Koneksi internet lambat. Silakan coba lagi")
                        )
                    }
                    response.code() == 500 -> {
                        _addChildState.postValue(
                            UIState.Error("Terjadi kesalahan di server. Silakan coba lagi nanti")
                        )
                    }
                    else -> {
                        _addChildState.postValue(
                            UIState.Error("Gagal menambahkan profil anak. Silakan coba lagi")
                        )
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> {
                        _addChildState.postValue(
                            UIState.Error("Tidak ada koneksi internet. Periksa jaringan anda")
                        )
                    }
                    is TimeoutException -> {
                        _addChildState.postValue(
                            UIState.Error("Waktu tunggu habis. Periksa koneksi internet anda")
                        )
                    }
                    else -> {
                        _addChildState.postValue(
                            UIState.Error("Terjadi kesalahan. Silakan coba lagi")
                        )
                    }
                }
            }
        }
    }

    fun getChildren() {
        viewModelScope.launch {
            _getChildrenState.postValue(UIState.Loading)
            try {
                val currentUserId = _userId.value ?: throw IllegalStateException("User ID is missing")
                val response = repository.getChildren(currentUserId)
                if (response.isSuccessful) {
                    _childrenData.postValue(response.body())
                    _getChildrenState.postValue(UIState.Success)
                } else {
                    _getChildrenState.postValue(UIState.Error(response.message() ?: "Unknown Error"))
                }
            } catch (e: Exception) {
                _getChildrenState.postValue(UIState.Error(e.message ?: "Error Occurred"))
            }
        }
    }
}