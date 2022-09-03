package com.sample.technews.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.sample.technews.data.repositories.NewsRepository
import com.sample.technews.data.paging.NewsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = newsRepository.pageSize,
        prefetchDistance = 1
    )

    fun getArticles() = Pager(pagingConfig) {
        NewsPagingSource(newsRepository)
    }.flow.cachedIn(viewModelScope)

}