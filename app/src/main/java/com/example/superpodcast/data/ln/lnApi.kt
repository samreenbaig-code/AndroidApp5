package com.example.superpodcast.data.ln

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface lnApi {
    // GET /search
    @GET("search")
    suspend fun search(
        @Query("q") q: String,
        @Query("type") type: String = "podcast",
        @Query("offset") offset: Int = 0,
        @Query("language") language: String? = null,
        @Query("safe_mode") safeMode: Int = 0 // 1 = hide explicit
    ): LnSearchResponse

    // GET /podcasts/{id}?sort=recent_first
    @GET("podcasts/{id}")
    suspend fun podcastById(
        @Path("id") id: String,
        @Query("sort") sort: String = "recent_first"
    ): LnPodcastDetails
}
