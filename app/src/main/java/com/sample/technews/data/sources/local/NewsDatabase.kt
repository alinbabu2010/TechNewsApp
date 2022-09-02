package com.sample.technews.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.technews.data.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}