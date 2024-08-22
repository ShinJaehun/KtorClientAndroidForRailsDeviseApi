package com.shinjaehun.ktorclientandroid.presentation.posts

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shinjaehun.ktorclientandroid.data.remote.PostsServiceImpl
import com.shinjaehun.ktorclientandroid.data.remote.dto.PostRequest
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

    fun CreatePost(userId: String){
        viewModelScope.launch {
            val client = PostsServiceImpl(httpClient)
            client.createPost(
                PostRequest(
                    body = "안녕~",
                    title="나야 나",
                    userId = userId.toInt()
                )
            )
            getPosts()
            // 이렇게 하면 안되는 거 아녜요? 상태만 바꿔야 하는 거 아닌가?
            // 되긴 되네요... 뭐 몇번 compose를 재구성하긴 하는거 같은데
        }
    }
}