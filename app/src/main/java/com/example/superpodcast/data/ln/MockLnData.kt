package com.example.superpodcast.data.ln

object MockLnData {

    fun searchResults(): List<LnSearchResult> = listOf(
        LnSearchResult(
            id = "mock_pod_1",
            title = "History Unpacked",
            publisher = "Mock Media",
            image = null,
            listenScore = 77.0,
            globalRank = 1200
        ),
        LnSearchResult(
            id = "mock_pod_2",
            title = "Kids Learning Stories",
            publisher = "Clever Cubs Studio",
            image = null,
            listenScore = 88.0,
            globalRank = 600
        ),
        LnSearchResult(
            id = "mock_pod_3",
            title = "Tech in 10 Minutes",
            publisher = "Dev Daily",
            image = null,
            listenScore = 71.0,
            globalRank = 2000
        )
    )

    fun podcastDetails(podcastId: String): LnPodcastDetails {
        val title = searchResults().firstOrNull { it.id == podcastId }?.title ?: "Mock Podcast"

        return LnPodcastDetails(
            id = podcastId,
            title = title,
            publisher = "Mock Publisher",
            description = "Mock podcast data (no API key needed).",
            image = null,
            episodes = listOf(
                LnEpisode(
                    id = "ep1",
                    title = "Episode 1: Introduction",
                    audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                    audioLengthSec = 210,
                    pubDateMs = System.currentTimeMillis() - 86400000
                ),
                LnEpisode(
                    id = "ep2",
                    title = "Episode 2: Deep Dive",
                    audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
                    audioLengthSec = 260,
                    pubDateMs = System.currentTimeMillis() - 172800000
                )
            )
        )
    }
}
