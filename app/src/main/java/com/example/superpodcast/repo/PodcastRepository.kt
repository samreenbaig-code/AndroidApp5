package com.example.superpodcast.repo

import com.example.superpodcast.data.db.SubscriptionDao
import com.example.superpodcast.data.db.SubscriptionEntity
import com.example.superpodcast.data.ln.LnPodcastDetails
import com.example.superpodcast.data.ln.LnSearchResult
import com.example.superpodcast.data.ln.lnNetwork
import kotlinx.coroutines.flow.Flow
// Repository pattern: UI calls repository, repository talks to network + Room database.

data class AdvancedCriteria(
    val regex: String = "",
    val minWords: Int = 0,
    val maxWords: Int = 999,
    val safeModeHideExplicit: Boolean = false,
    val language: String? = null,
    val sortMode: SortMode = SortMode.RELEVANCE
)

enum class SortMode { RELEVANCE, LOWEST_SCORE, HIGHEST_SCORE, LOWEST_RANK, HIGHEST_RANK }

class PodcastRepository(private val dao: SubscriptionDao) {

    fun observeSubscriptions(): Flow<List<SubscriptionEntity>> = dao.observeAll()

    suspend fun search(term: String, c: AdvancedCriteria): List<LnSearchResult> {
        val resp = lnNetwork.api.search(
            q = term,
            type = "podcast",
            offset = 0,
            language = c.language,
            safeMode = if (c.safeModeHideExplicit) 1 else 0
        )

        // ✅ IMPORTANT: use resp.results (NOT searchResults)
        val filtered: List<LnSearchResult> = resp.results
            .filter { r ->
                val words = r.title.trim().split(Regex("\\s+")).filter { it.isNotBlank() }.size
                words in c.minWords..c.maxWords
            }
            .filter { r ->
                if (c.regex.isBlank()) return@filter true
                val re = runCatching { Regex(c.regex, RegexOption.IGNORE_CASE) }.getOrNull()
                re?.containsMatchIn(r.title + " " + (r.publisher ?: "")) ?: true
            }

        return when (c.sortMode) {
            SortMode.RELEVANCE -> filtered
            SortMode.LOWEST_SCORE -> filtered.sortedBy { it.listenScore ?: Double.MAX_VALUE }
            SortMode.HIGHEST_SCORE -> filtered.sortedByDescending { it.listenScore ?: Double.MIN_VALUE }
            SortMode.LOWEST_RANK -> filtered.sortedBy { it.globalRank ?: Int.MAX_VALUE }
            SortMode.HIGHEST_RANK -> filtered.sortedByDescending { it.globalRank ?: Int.MIN_VALUE }
        }
    }

    suspend fun podcastDetails(id: String): LnPodcastDetails =
        lnNetwork.api.podcastById(id)

    suspend fun subscribe(r: LnSearchResult) {
        dao.upsert(
            SubscriptionEntity(
                podcastId = r.id,
                title = r.title,
                publisher = r.publisher,
                image = r.image
            )
        )
    }

    suspend fun unsubscribe(entity: SubscriptionEntity) {
        dao.delete(entity)
    }

    suspend fun isSubscribed(id: String): Boolean = dao.isSubscribed(id)
}
