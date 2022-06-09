package com.example.hw7.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.hw7.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PreferencesRepository {

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_PUSH_TOKEN = "push_token"
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    override fun addToken(token: String) {
        sharedPreferences.edit {
            putString(KEY_ACCESS_TOKEN, token)
        }
    }

    override fun deleteToken() {
        sharedPreferences.edit {
            remove(KEY_ACCESS_TOKEN)
        }
    }

    override fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    override fun addUserId(userId: String) {
        sharedPreferences.edit {
            putString(KEY_USER_ID, userId)
        }
    }

    override fun deleteUserId() {
        sharedPreferences.edit {
            remove(KEY_USER_ID)
        }
    }

    override fun addPushToken(pushToken: String) {
        sharedPreferences.edit {
            putString(KEY_PUSH_TOKEN, pushToken)
        }
    }

    override fun deletePushToken() {
        sharedPreferences.edit {
            remove(KEY_PUSH_TOKEN)
        }
    }

    override fun getPushToken(): String? {
        return sharedPreferences.getString(KEY_PUSH_TOKEN, null)
    }
}