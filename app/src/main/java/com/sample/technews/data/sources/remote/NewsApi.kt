package com.sample.technews.data.sources.remote

import com.sample.technews.data.models.NewsApiResponse
import com.sample.technews.data.sources.remote.ApiConstants.API_ENDPOINT
import com.sample.technews.data.sources.remote.ApiConstants.PARAM_API_KEY
import com.sample.technews.data.sources.remote.ApiConstants.PARAM_PAGE
import com.sample.technews.data.sources.remote.ApiConstants.PARAM_PAGE_SIZE
import com.sample.technews.data.sources.remote.ApiConstants.PARAM_QUERY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * API interface for news
 */
interface NewsApi {

    @GET(API_ENDPOINT)
    fun getTechNews(
        @Header(PARAM_API_KEY) key: String,
        @Query(PARAM_QUERY) topic: String,
        @Query(PARAM_PAGE) page: Int,
        @Query(PARAM_PAGE_SIZE) size: Int
    ): Call<NewsApiResponse>

}