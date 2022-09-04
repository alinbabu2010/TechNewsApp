package com.sample.technews.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.technews.data.models.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<Article>)

    @Query("DELETE FROM Article")
    fun deleteArticles()

    @Query("SELECT * FROM Article")
    fun getArticles(): List<Article>

}