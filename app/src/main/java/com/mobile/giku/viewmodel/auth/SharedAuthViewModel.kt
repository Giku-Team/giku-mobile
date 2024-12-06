package com.mobile.giku.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedAuthViewModel : ViewModel() {

    private val _email = MutableLiveData<String?>()
    val email: LiveData<String?> = _email

    fun setEmail(value: String?) {
        _email.value = value
    }
}