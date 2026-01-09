package com.example.superpodcast.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.superpodcast.data.ln.LnSearchResult
import com.example.superpodcast.repo.AdvancedCriteria
import com.example.superpodcast.repo.PodcastRepository
import com.example.superpodcast.repo.SortMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    repo: PodcastRepository,
    onOpenSubscriptions: () -> Unit,
    onOpenDetails: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    var term by remember { mutableStateOf("history") }
    var regex by remember { mutableStateOf("") }
    var minWords by remember { mutableStateOf("0") }
    var maxWords by remember { mutableStateOf("20") }
    var safeMode by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("") }
    var sortMode by remember { mutableStateOf(SortMode.RELEVANCE) }

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var results by remember { mutableStateOf<List<LnSearchResult>>(emptyList()) }

    fun doSearch() {
        loading = true
        error = null
        scope.launch {
            try {
                val criteria = AdvancedCriteria(
                    regex = regex.trim(),
                    minWords = minWords.toIntOrNull() ?: 0,
                    maxWords = maxWords.toIntOrNull() ?: 999,
                    safeModeHideExplicit = safeMode,
                    language = language.trim().ifBlank { null },
                    sortMode = sortMode
                )
                results = repo.search(term.trim().ifBlank { "podcast" }, criteria)
            } catch (t: Throwable) {
                error = t.message ?: "Search failed"
            } finally {
                loading = false
            }
        }
    }

    LaunchedEffect(Unit) { doSearch() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Super Podcast (Mock)") },
                actions = { TextButton(onClick = onOpenSubscriptions) { Text("Subscriptions") } }
            )
        }
    ) { pad ->
        Column(
            Modifier.padding(pad).padding(16.dp).fillMaxSize()
        ) {
            OutlinedTextField(
                value = term,
                onValueChange = { term = it },
                label = { Text("Search term") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = regex,
                onValueChange = { regex = it },
                label = { Text("Regex (optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = minWords,
                    onValueChange = { minWords = it },
                    label = { Text("Min words") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = maxWords,
                    onValueChange = { maxWords = it },
                    label = { Text("Max words") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = language,
                    onValueChange = { language = it },
                    label = { Text("Language (en/fr) optional") },
                    modifier = Modifier.weight(1f)
                )
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Hide explicit")
                    Switch(checked = safeMode, onCheckedChange = { safeMode = it })
                }
            }

            Spacer(Modifier.height(8.dp))
            SortDropdown(sortMode = sortMode, onChange = { sortMode = it })

            Spacer(Modifier.height(12.dp))
            Button(
                onClick = { doSearch() },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (loading) "Searching..." else "Search")
            }

            error?.let {
                Spacer(Modifier.height(10.dp))
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize()) {
                items(results) { r ->
                    Card(
                        Modifier.fillMaxWidth().clickable { onOpenDetails(r.id) }
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Text(r.title, style = MaterialTheme.typography.titleMedium)
                            Text(r.publisher ?: "Unknown", style = MaterialTheme.typography.bodyMedium)
                            Text("Score: ${r.listenScore ?: "n/a"} • Rank: ${r.globalRank ?: "n/a"}")
                            Spacer(Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Button(onClick = { onOpenDetails(r.id) }) { Text("Open") }
                                OutlinedButton(onClick = { scope.launch { repo.subscribe(r) } }) { Text("Subscribe") }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SortDropdown(sortMode: SortMode, onChange: (SortMode) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = SortMode.entries

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = sortMode.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Sort") },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt.name) },
                    onClick = { onChange(opt); expanded = false }
                )
            }
        }
    }
}
