package com.example.superpodcast.data.ln

object MockLnData {

    // Returns search results for the Search screen
    fun searchResponse(q: String): LnSearchResponse {
        val term = q.trim().ifBlank { "history" }

        val results = listOf(
            LnSearchResult(
                id = "mock_pod_1",
                title = "History Unpacked ($term)",
                publisher = "Mock Media",
                image = null,
                listenScore = 77.0,
                globalRank = 1200
            ),
            LnSearchResult(
                id = "mock_pod_2",
                title = "Kids Learning Stories ($term)",
                publisher = "Clever Cubs Studio",
                image = null,
                listenScore = 88.0,
                globalRank = 600
            ),
            LnSearchResult(
                id = "mock_pod_3",
                title = "Tech in 10 Minutes ($term)",
                publisher = "Dev Daily",
                image = null,
                listenScore = 71.0,
                globalRank = 2000
            )
        )

        return LnSearchResponse(
            count = results.size,
            results = results
        )
    }
    // Mock server data used for development/testing without a real API key.

    // Returns details + EPISODES for the Details screen
    fun podcastDetails(podcastId: String): LnPodcastDetails {
        val now = System.currentTimeMillis()

        val episodes = listOf(
            LnEpisode(
                id = "ep_1",
                title = "JFK – Ich bin ein Berliner (Speech)",
                pubDateMs = now - 7L * 24 * 60 * 60 * 1000,
                audioUrl = "https://commons.wikimedia.org/wiki/Special:FilePath/Jfk_berlin_address_high.ogg",
                audioLengthSec = 562
            ),
            LnEpisode(
                id = "ep_2",
                title = "Nixon – Resignation Speech (Speech)",
                pubDateMs = now - 3L * 24 * 60 * 60 * 1000,
                audioUrl = "https://commons.wikimedia.org/wiki/Special:FilePath/Nixon%20resignation%20audio%20with%20buzz%20removed.ogg",
                audioLengthSec = 922
            )
        )


        return LnPodcastDetails(
            id = podcastId,
            title = "Mock Podcast Details",
            publisher = "Mock Publisher",
            description = "This is mock podcast data used for Assignment 8.",
            image = null,
            episodes = episodes
        )
    }
}
