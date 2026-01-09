package com.example.superpodcast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.superpodcast.data.db.DbProvider
import com.example.superpodcast.repo.PodcastRepository
import com.example.superpodcast.ui.AppRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DbProvider.create(applicationContext)
        val repo = PodcastRepository(db.subscriptionDao())

        setContent { AppRoot(repo) }
    }
}
