package com.example.superpodcast.data.ln

class MockLnApi : LnApi {

    override suspend fun search(
        q: String,
        type: String,
        offset: Int,
        language: String?,
        safeMode: Int
    ): LnSearchResponse {
        return MockLnData.searchResponse(q)
    }

    override suspend fun podcastById(
        id: String,
        sort: String
    ): LnPodcastDetails {
        return MockLnData.podcastDetails(podcastId = id)

    }
}
