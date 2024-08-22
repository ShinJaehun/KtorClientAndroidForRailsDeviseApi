package com.shinjaehun.ktorclientandroid.data.remote

import com.shinjaehun.ktorclientandroid.data.remote.dto.PostRequest
import com.shinjaehun.ktorclientandroid.data.remote.dto.SignInRequest
import com.shinjaehun.ktorclientandroid.util.Resource

interface AuthService {

    suspend fun signIn(signInRequest: SignInRequest): Resource<Unit>

}