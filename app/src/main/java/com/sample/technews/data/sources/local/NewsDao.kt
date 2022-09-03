package com.sample.technews.data.sources.local

import androidx.room.*
import com.sample.technews.data.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<Article>)

    @Query("DELETE FROM Article")
    fun deleteArticles()

    @Query("SELECT * FROM Article")
    fun getArticles(): List<Article>

}