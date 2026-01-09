package com.example.superpodcast.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.superpodcast.data.ln.LnEpisode
import com.example.superpodcast.data.ln.LnPodcastDetails
import com.example.superpodcast.repo.PodcastRepository
import com.example.superpodcast.ui.player.AudioPlayerBar
import java.text.SimpleDateFormat
import java.util.*




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    repo: PodcastRepository,
    podcastId: String,
    onBack: () -> Unit
) {

    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var details by remember { mutableStateOf<LnPodcastDetails?>(null) }

    var currentUrl by remember { mutableStateOf<String?>(null) }
    var currentTitle by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(podcastId) {
        loading = true
        error = null
        try {
            details = repo.podcastDetails(podcastId)
        } catch (t: Throwable) {
            error = t.message ?: "Failed to load podcast"
        } finally {
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(details?.title ?: "Podcast") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        },
        bottomBar = { AudioPlayerBar(audioUrl = currentUrl, title = currentTitle) }
    ) { pad ->
        Column(Modifier.padding(pad).padding(16.dp).fillMaxSize()) {
            if (loading) {
                CircularProgressIndicator()
                return@Column
            }
            error?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
                return@Column
            }

            val d = details ?: return@Column

            Text(d.title, style = MaterialTheme.typography.titleLarge)
            Text(d.publisher ?: "Unknown")
            Spacer(Modifier.height(10.dp))

            if (!d.description.isNullOrBlank()) {
                Text(d.description!!)
                Spacer(Modifier.height(12.dp))
            }

            Text("Episodes", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(d.episodes) { ep ->
                    EpisodeCard(ep = ep) { url, title ->
                        currentUrl = url
                        currentTitle = title
                    }
                }
                item { Spacer(Modifier.height(90.dp)) }
            }
        }
    }
}

@Composable
private fun EpisodeCard(
    ep: LnEpisode,
    onPlay: (String, String) -> Unit
) {
    val playable = !ep.audioUrl.isNullOrBlank()
    val date = ep.pubDateMs?.let {
        SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(it))
    } ?: "N/A"
    val dur = ep.audioLengthSec?.let { "${it / 60}m" } ?: "N/A"

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(14.dp)) {
            Text(ep.title, style = MaterialTheme.typography.titleMedium)
            Text("Date: $date • Duration: $dur")
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = { onPlay(ep.audioUrl!!, ep.title) },
                enabled = playable
            ) {
                Text(if (playable) "Play" else "No audio")
            }
        }
    }
}
