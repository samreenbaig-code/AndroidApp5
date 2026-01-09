package com.example.superpodcast.ui.player

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun AudioPlayerBar(
    audioUrl: String?,
    title: String?
) {
    val context = LocalContext.current
    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    var isPlaying by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose { player.release() }
    }

    LaunchedEffect(audioUrl) {
        if (!audioUrl.isNullOrBlank()) {
            val item = MediaItem.fromUri(Uri.parse(audioUrl))
            player.setMediaItem(item)
            player.prepare()
            player.playWhenReady = true
            isPlaying = true
        }
    }

    if (audioUrl.isNullOrBlank()) {
        // show nothing if no episode selected
        return
    }

    Surface(tonalElevation = 4.dp) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(title ?: "Playing", style = MaterialTheme.typography.titleSmall)
                Text(audioUrl.take(60) + "...", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(Modifier.width(10.dp))
            Button(onClick = {
                isPlaying = if (isPlaying) {
                    player.pause()
                    false
                } else {
                    player.play()
                    true
                }
            }) {
                Text(if (isPlaying) "Pause" else "Play")
            }
        }
    }
}
