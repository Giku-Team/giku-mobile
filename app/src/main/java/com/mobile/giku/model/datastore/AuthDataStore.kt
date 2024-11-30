package com.mobile.giku.model.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mobile.giku.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(BuildConfig.AUTH_PREFERENCES)

class AuthDataStore(private val context: Context) {

    private val loginStatusKey = booleanPreferencesKey(BuildConfig.LOGIN_STATUS)

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[loginStatusKey] ?: false
    }

    suspend fun setLoginStatus(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[loginStatusKey] = isLoggedIn
        }
    }

    suspend fun clearLoginStatus() {
        context.dataStore.edit { preferences ->
            preferences.remove(loginStatusKey)
        }
    }
}