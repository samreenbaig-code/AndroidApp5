package com.example.superpodcast.data.ln

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LnSearchResponse(
    @SerialName("count") val count: Int = 0,
    @SerialName("results") val results: List<LnSearchResult> = emptyList()
)

@Serializable
data class LnSearchResult(
    val id: String,
    val title: String,
    val publisher: String?,
    val image: String?,
    val listenScore: Double?,
    val globalRank: Int?
)


@Serializable
data class LnPodcastDetails(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("publisher") val publisher: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("episodes") val episodes: List<LnEpisode> = emptyList()
)

@Serializable
data class LnEpisode(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("pub_date_ms") val pubDateMs: Long? = null,
    @SerialName("audio") val audioUrl: String? = null,
    @SerialName("audio_length_sec") val audioLengthSec: Int? = null
)
