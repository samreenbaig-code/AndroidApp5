package com.example.superpodcast.data.ln

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

object lnNetwork {
// Network layer. For Assignment 8 we use MockLnApi to avoid API key (HTTP 401).

    // ✅ turn this ON to avoid HTTP 401 while finishing assignment
    private const val USE_MOCK = true

    private const val BASE_URL = "https://listen-api.listennotes.com/api/v2/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val client = OkHttpClient.Builder().build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val api: LnApi by lazy {
        if (USE_MOCK) MockLnApi()
        else retrofit.create(LnApi::class.java)
    }
}
