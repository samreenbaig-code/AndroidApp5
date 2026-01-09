package com.example.superpodcast.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.superpodcast.repo.PodcastRepository
import com.example.superpodcast.ui.screens.DetailsScreen
import com.example.superpodcast.ui.screens.SearchScreen
import com.example.superpodcast.ui.screens.SubscriptionsScreen

sealed class Route(val id: String) {
    data object Search : Route("search")
    data object Subs : Route("subs")
    data object Details : Route("details/{podcastId}") {
        fun create(podcastId: String) = "details/$podcastId"
    }
}

@Composable
fun AppRoot(repo: PodcastRepository) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Route.Search.id) {
        composable(Route.Search.id) {
            SearchScreen(
                repo = repo,
                onOpenSubscriptions = { nav.navigate(Route.Subs.id) },
                onOpenDetails = { id -> nav.navigate(Route.Details.create(id)) }
            )
        }
        composable(Route.Subs.id) {
            SubscriptionsScreen(
                repo = repo,
                onBack = { nav.popBackStack() },
                onOpenDetails = { id -> nav.navigate(Route.Details.create(id)) }
            )
        }
        composable(Route.Details.id) { backStack ->
            val id = backStack.arguments?.getString("podcastId") ?: ""
            DetailsScreen(repo = repo, podcastId = id, onBack = { nav.popBackStack() })
        }
    }
}
