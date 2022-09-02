package com.sample.technews.data.sources.local

import androidx.room.*
import com.sample.technews.data.models.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<Article>)

    @Delete
    fun deleteArticles(articles: List<Article>)

    @Query("SELECT * FROM Article")
    fun getArticles()

}