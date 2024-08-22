package com.shinjaehun.ktorclientandroid.data.remote

import android.util.Log
import com.shinjaehun.ktorclientandroid.data.remote.dto.PostRequest
import com.shinjaehun.ktorclientandroid.data.remote.dto.PostResponse
import com.shinjaehun.ktorclientandroid.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val TAG = "PostsServiceImpl"

class PostsServiceImpl(
    private val client: HttpClient
) : PostsService {

    override suspend fun getPosts(): Resource<List<PostResponse>> {
        return try {
            val httpResponse = client.get {
                url(HttpRoutes.POSTS)
            }
            Resource.Success(data = httpResponse.body())
        } catch(e: RedirectResponseException) {
            // 3xx -response
//            println("3xx Error: ${e.response.status.description}")
            Resource.Error(message = e.message.toString())
        } catch(e: ClientRequestException) {
            // 4xx -response
//            println("4xx Error: ${e.response.status.description}")
            Resource.Error(message = e.message.toString())
        } catch(e: ServerResponseException) {
            // 5xx -response
//            println("5xx Error: ${e.response.status.description}")
            Resource.Error(message = e.message.toString())
        } catch (e: Exception) {
//            println("Error: ${e.message}")
            Resource.Error(message = e.message.toString())
        }
    }

    override suspend fun createPost(postRequest: PostRequest): Resource<Unit> {
        return try {
            val httpResponse = client.post(HttpRoutes.POSTS) {
                contentType(ContentType.Application.Json)
                setBody(postRequest)
            }
            if (httpResponse.status.value in 200..299) {
                Resource.Success(Unit)
            } else {
                Resource.Error(message = "http error: ${httpResponse.status.value}")
            }
        } catch (e: Exception) {
//            Log.e(TAG, "Error: ${e.message}")
            Resource.Error(message = "Error: ${e.message}")
        }
    }
}