package com.sample.technews.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.sample.technews.data.paging.NewsPagingSource
import com.sample.technews.data.repositories.NewsRepository
import com.sample.technews.domain.GetArticleWithFormattedDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    @Inject
    lateinit var getArticleWithFormattedDateUseCase: GetArticleWithFormattedDateUseCase

    private val pagingConfig = PagingConfig(
        pageSize = newsRepository.pageSize,
        prefetchDistance = 1
    )

    fun getArticles() = Pager(pagingConfig) {
        NewsPagingSource(newsRepository)
    }.flow.map { data ->
        data.map { getArticleWithFormattedDateUseCase(it) }
    }.cachedIn(viewModelScope)

}