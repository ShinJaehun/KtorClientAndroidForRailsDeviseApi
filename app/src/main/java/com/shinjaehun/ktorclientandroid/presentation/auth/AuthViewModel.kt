package com.shinjaehun.ktorclientandroid.presentation.auth

import android.content.Context
import android.telecom.Call
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shinjaehun.ktorclientandroid.data.remote.AuthServiceImpl
import com.shinjaehun.ktorclientandroid.data.remote.dto.PostRequest
import com.shinjaehun.ktorclientandroid.data.remote.dto.SignInRequest
import com.shinjaehun.ktorclientandroid.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

private const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val httpClient: HttpClient
) : ViewModel() {

//    private val _email = mutableStateOf(String())
//    val email: State<String> = _email
//
//    private val _password = mutableStateOf(String())
//    val password: State<String> = _password

    fun performSignIn(context: Context, email: String, password: String, onNavigate: (String)->Unit) {
        viewModelScope.launch {
            try {
               val client = AuthServiceImpl(httpClient)
                when(val result = client.signIn(SignInRequest(email=email, password=password))){
                    is Resource.Success -> {
                        Log.i(TAG, "Successful response!")
                        Log.i(TAG, "login user id: ${result.data}")
                        onNavigate("posts_screen/${result.data}")
                    }
                    else -> {
                        Log.e(TAG, "Error: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "ERROR: $e.message")
            }
        }
    }
}