package com.shinjaehun.ktorclientandroid.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val token: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("resource_owner")
    val resourceOwner:  Map<String, String>,
)