package com.example.superpodcast.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey val podcastId: String,
    val title: String,
    val publisher: String?,
    val image: String?
)
