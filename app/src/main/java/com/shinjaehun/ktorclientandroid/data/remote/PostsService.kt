package com.shinjaehun.ktorclientandroid.data.remote

import com.shinjaehun.ktorclientandroid.data.remote.dto.PostRequest
import com.shinjaehun.ktorclientandroid.data.remote.dto.PostResponse
import com.shinjaehun.ktorclientandroid.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.serializer
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface PostsService {

    suspend fun getPosts(): Resource<List<PostResponse>>

    suspend fun createPost(postRequest: PostRequest): Resource<Unit>

}