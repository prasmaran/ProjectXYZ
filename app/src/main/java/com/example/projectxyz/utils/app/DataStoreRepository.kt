package com.example.projectxyz.utils.app

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
class DataStoreRepository (val context: Context) {

    private object PreferenceKeys {
        val userLoginTime: Preferences.Key<Long> = longPreferencesKey("user_login_time")
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveUserLoginTime(time: Long) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.userLoginTime] = time
        }
    }

    val userLoginTime = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.userLoginTime] ?: 0
    }

    suspend fun clearUserLoginTime() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.userLoginTime)
        }
    }

    suspend fun clearAllDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }





}