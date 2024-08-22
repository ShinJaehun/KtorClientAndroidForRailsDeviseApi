package com.shinjaehun.ktorclientandroid.presentation.posts

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shinjaehun.ktorclientandroid.data.remote.PostsServiceImpl
import com.shinjaehun.ktorclientandroid.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PostsViewModel"

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val httpClient: HttpClient

): ViewModel() {

    private val _state = mutableStateOf(PostsState())
    val state: State<PostsState> = _state

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            try {
                val client = PostsServiceImpl(client=httpClient)

                _state.value = state.value.copy(isLoading = true)

                when(val result = client.getPosts()){
                    is Resource.Error -> {
                        Log.e(TAG, "Error: ${result.message}")
                        _state.value = state.value.copy(isLoading = false)
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG, "Successful response!")
                        result.data?.let {
                            _state.value = state.value.copy(
                                postResponse = it,
                                isLoading = false,
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "ERROR: ${e.message}")
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }
}