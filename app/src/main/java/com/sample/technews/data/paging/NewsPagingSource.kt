package com.sample.technews.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sample.technews.data.models.Article
import com.sample.technews.data.models.Resource
import com.sample.technews.data.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsPagingSource(
    private val newsRepository: NewsRepository
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        return try {

            val nextPage = params.key ?: 1
            when (val newsResponse = withContext(Dispatchers.IO) {
                newsRepository.getArticlesList(nextPage)
            }) {
                is Resource.Success -> {
                    LoadResult.Page(
                        data = newsResponse.data ?: emptyList(),
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = if (nextPage < 10) nextPage + 1 else null
                    )
                }
                is Resource.Error -> {
                    LoadResult.Error(newsResponse.exception)
                }
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}