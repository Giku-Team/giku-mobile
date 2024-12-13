package com.mobile.giku.model.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.auth0.android.jwt.JWT
import com.mobile.giku.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(BuildConfig.AUTH_PREFERENCES)

class AuthDataStore(private val context: Context) {

    private val tokenKey = stringPreferencesKey(BuildConfig.TOKEN)
    private val loginStatusKey = stringPreferencesKey(BuildConfig.LOGIN_STATUS)

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    fun getToken(): Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[tokenKey] }

    suspend fun setLoginStatus(status: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[loginStatusKey] = status.toString()
        }
    }

    fun getLoginStatus(): LiveData<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[loginStatusKey]?.toBoolean() ?: false
        }.asLiveData()

    suspend fun clearAuthData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun getUserId(): Flow<String?> = context.dataStore.data
        .map { preferences ->
            val token = preferences[tokenKey]
            token?.let { JWT(it).getClaim("userId").asString() }
        }
}
