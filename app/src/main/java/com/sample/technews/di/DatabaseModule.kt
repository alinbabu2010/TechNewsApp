package com.sample.technews.di

import android.content.Context
import androidx.room.Room
import com.sample.technews.data.sources.local.NewsDao
import com.sample.technews.data.sources.local.NewsDatabase
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext

object DatabaseModule {

    private const val DB_NAME = "tech_news_db"

    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao()
    }


}