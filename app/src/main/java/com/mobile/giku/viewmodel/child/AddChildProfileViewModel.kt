package com.mobile.giku.viewmodel.child

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.model.remote.child.AddChildRequest
import com.mobile.giku.model.remote.child.GetChildrenResponse
import com.mobile.giku.model.remote.generic.GenericResponse
import com.mobile.giku.repository.child.ChildRepository
import com.mobile.giku.viewmodel.state.UIState
import kotlinx.coroutines.launch
import retrofit2.Response

class AddChildProfileViewModel(
    private val repository: ChildRepository
) : ViewModel() {

    private val _addChildState = MutableLiveData<UIState>()
    val addChildState: LiveData<UIState> get() = _addChildState

    private val _getChildrenState = MutableLiveData<UIState>()
    val getChildrenState: LiveData<UIState> get() = _getChildrenState

    private val _childrenData = MutableLiveData<GetChildrenResponse?>()
    val childrenData: LiveData<GetChildrenResponse?> get() = _childrenData

    fun addChildProfile(userId: String, addChildRequest: AddChildRequest) {
        _addChildState.value = UIState.Loading
        viewModelScope.launch {
            try {
                val response: Response<GenericResponse> = repository.addChildProfile(userId, addChildRequest)
                if (response.isSuccessful) {
                    _addChildState.value = UIState.Success
                } else {
                    _addChildState.value = UIState.Error(response.message() ?: "Unknown Error")
                }
            } catch (e: Exception) {
                _addChildState.value = UIState.Error(e.message ?: "Error Occurred")
            }
        }
    }

    fun getChildren(userId: String) {
        _getChildrenState.value = UIState.Loading
        viewModelScope.launch {
            try {
                val response: Response<GetChildrenResponse> = repository.getChildren(userId)
                if (response.isSuccessful) {
                    _childrenData.value = response.body()
                    _getChildrenState.value = UIState.Success
                } else {
                    _getChildrenState.value = UIState.Error(response.message() ?: "Unknown Error")
                }
            } catch (e: Exception) {
                _getChildrenState.value = UIState.Error(e.message ?: "Error Occurred")
            }
        }
    }
}
