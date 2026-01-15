package com.example.superpodcast.data.ln

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
// Retrofit interface for podcast endpoints (networking example)

interface LnApi {

    suspend fun search(
        @Query("q") q: String,
        @Query("type") type: String = "podcast",
        @Query("offset") offset: Int = 0,
        @Query("language") language: String? = null,
        @Query("safe_mode") safeMode: Int = 0
    ): LnSearchResponse

    suspend fun podcastById(
        @Path("id") id: String,
        @Query("sort") sort: String = "recent_first"
    ): LnPodcastDetails
}

