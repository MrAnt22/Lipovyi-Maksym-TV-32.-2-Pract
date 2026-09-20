package com.example.batterystorage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(
    name = "user_preferences"
)

class UserPreferences(
    private val context: Context
) {

    companion object {
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
    }

    suspend fun saveUser(
        name: String,
        email: String,
        password: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[EMAIL_KEY] = email
            preferences[PASSWORD_KEY] = password
        }
    }

    suspend fun getName(): String {
        val preferences = context.dataStore.data.first()
        return preferences[NAME_KEY] ?: ""
    }

    suspend fun getEmail(): String {
        val preferences = context.dataStore.data.first()
        return preferences[EMAIL_KEY] ?: ""
    }

    suspend fun getPassword(): String {
        val preferences = context.dataStore.data.first()
        return preferences[PASSWORD_KEY] ?: ""
    }

    suspend fun checkUser(
        email: String,
        password: String
    ): Boolean {
        val preferences = context.dataStore.data.first()

        val savedEmail = preferences[EMAIL_KEY] ?: ""
        val savedPassword = preferences[PASSWORD_KEY] ?: ""

        return email.trim() == savedEmail.trim() &&
                password == savedPassword
    }
}