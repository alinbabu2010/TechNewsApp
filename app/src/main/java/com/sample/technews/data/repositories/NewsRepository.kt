package com.sample.technews.data.repositories

import com.sample.technews.data.models.Article
import com.sample.technews.data.models.NewsApiResponse
import com.sample.technews.data.models.Resource
import com.sample.technews.data.sources.local.NewsDao
import com.sample.technews.data.sources.remote.NewsRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsDao: NewsDao
) {

    private var _pageSize = 10
    val pageSize = _pageSize

    suspend fun getArticlesList(page: Int): Resource<NewsApiResponse> {
        return newsRemoteDataSource.getTechNews(page, pageSize)
    }

    fun addArticleToDb(articles: List<Article>?) {
        articles?.let {
            newsDao.insertArticles(articles)
        }
    }

}