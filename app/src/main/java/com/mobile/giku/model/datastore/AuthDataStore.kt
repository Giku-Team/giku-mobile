package com.mobile.giku.model.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mobile.giku.BuildConfig
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(BuildConfig.AUTH_PREFERENCES)

class AuthDataStore(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey(BuildConfig.TOKEN)
    private val LOGIN_STATUS_KEY = stringPreferencesKey(BuildConfig.LOGIN_STATUS)

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    suspend fun setLoginStatus(status: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_STATUS_KEY] = status.toString()
        }
    }

    fun getLoginStatus(): LiveData<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[LOGIN_STATUS_KEY]?.toBoolean() ?: false
        }.asLiveData()

    suspend fun clearAuthData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
