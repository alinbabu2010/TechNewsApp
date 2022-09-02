package com.sample.technews.data.sources.remote

import com.sample.technews.data.models.NewsApiResponse
import com.sample.technews.data.models.Resource

interface NewsApiSource {
    suspend fun getTechNews(page: Int, pageSize: Int): Resource<NewsApiResponse>
}