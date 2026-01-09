package com.example.superpodcast.data.db

import android.content.Context
import androidx.room.Room

object DbProvider {
    fun create(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "superpodcast.db").build()
}
