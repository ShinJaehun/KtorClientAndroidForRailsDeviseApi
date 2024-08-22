package com.shinjaehun.ktorclientandroid.data.remote

import android.util.Log
import com.shinjaehun.ktorclientandroid.data.remote.dto.SignInRequest
import com.shinjaehun.ktorclientandroid.data.remote.dto.SignInResponse
import com.shinjaehun.ktorclientandroid.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val TAG = "AuthServiceImpl"

class AuthServiceImpl(
    private val client: HttpClient,
): AuthService {

    override suspend fun signIn(signInRequest: SignInRequest): Resource<String> {
        return try {
            val httpResponse = client.post(HttpRoutes.SIGN_IN) {
                contentType(ContentType.Application.Json)
                setBody(signInRequest)
            }
            if (httpResponse.status.value in 200..299) {
//                Log.i(TAG, "Successful response!")
//                Log.i(TAG, "body: ${httpResponse.bodyAsText()}")
                Log.i(TAG, "body: ${httpResponse.body<SignInResponse>()}")
                // 야 이렇게 하니까 받아 온다 야!!!
                Log.i(TAG, "id: ${httpResponse.body<SignInResponse>().resourceOwner["id"]}")
                Resource.Success(httpResponse.body<SignInResponse>().resourceOwner["id"])

            } else {
//                Log.e(TAG, "http error: ${httpResponse.status.value}")
                Resource.Error(message = "http error: ${httpResponse.status.value}")
            }
        } catch (e: Exception) {
//            Log.e(TAG, "Error: ${e.message}")
            Resource.Error(message = "Error: ${e.message}")
        }
    }
}