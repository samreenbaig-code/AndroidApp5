package com.example.superpodcast.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.superpodcast.repo.PodcastRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionsScreen(
    repo: PodcastRepository,
    onBack: () -> Unit,
    onOpenDetails: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val subs by repo.observeSubscriptions().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subscriptions") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(16.dp).fillMaxSize()) {
            Text("Saved: ${subs.size}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(10.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(subs) { s ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(14.dp)) {
                            Text(s.title, style = MaterialTheme.typography.titleMedium)
                            Text(s.publisher ?: "Unknown")
                            Spacer(Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Button(onClick = { onOpenDetails(s.podcastId) }) { Text("Open") }
                                OutlinedButton(onClick = { scope.launch { repo.unsubscribe(s) } }) { Text("Unsubscribe") }
                            }
                        }
                    }
                }
            }
        }
    }
}
