package com.sample.technews.data.repositories

import com.sample.technews.data.models.Article
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

    suspend fun getArticlesList(page: Int): Resource<List<Article>> {
        return when (val response = newsRemoteDataSource.getTechNews(page, pageSize)) {
            is Resource.Error -> if (page == 1) {
                Resource.Success(newsDao.getArticles())
            } else {
                Resource.Error(response.exception)
            }
            is Resource.Success -> {
                if (page == 1) {
                    addArticleToDb(response.data?.articles)
                }
                Resource.Success(response.data?.articles)
            }

        }
    }

    private fun addArticleToDb(articles: List<Article>?) {
        newsDao.deleteArticles()
        articles?.let {
            newsDao.insertArticles(articles)
        }
    }

}