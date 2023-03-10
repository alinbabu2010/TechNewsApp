package com.sample.technews.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.sample.technews.R
import com.sample.technews.domain.model.ArticleInfo
import com.sample.technews.ui.navigation.navigateToDetails
import com.sample.technews.ui.utils.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreen(lazyNewsItems: LazyPagingItems<ArticleInfo>) {

    var isRefreshing by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        val swipeRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
            lazyNewsItems.refresh()
            isRefreshing = true
        })

        Box(Modifier.pullRefresh(swipeRefreshState)) {

            LazyColumn {
                items(lazyNewsItems) { article ->
                    NewsItem(article = article) {
                        navigateToDetails(it)
                    }
                }

                lazyNewsItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                        }
                        loadState.append is LoadState.Loading -> {
                            item { LoadingItem() }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = lazyNewsItems.loadState.refresh as LoadState.Error
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage!!,
                                    modifier = Modifier.fillParentMaxSize(),
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = lazyNewsItems.loadState.append as LoadState.Error
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage!!,
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                        loadState.source.refresh is LoadState.NotLoading -> {
                            isRefreshing = false
                        }
                    }
                }

            }

            PullRefreshIndicator(
                isRefreshing,
                swipeRefreshState,
                Modifier.align(Alignment.TopCenter)
            )

        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsItem(article: ArticleInfo?, onItemClick: (ArticleInfo) -> Unit) {

    Card(
        shape = RoundedCornerShape(newsItemCardCornerSize),
        onClick = { article?.let { onItemClick(it) } },
        modifier = Modifier
            .padding(
                horizontal = newsImageCardHorizontalPadding,
                vertical = newsImageCardVerticalPadding
            )
            .fillMaxWidth(),
        backgroundColor = Color.LightGray
    )

    {
        Column(Modifier.padding(bottom = newsColumnBottomPadding)) {

            Image(
                painter = rememberAsyncImagePainter(
                    model = article?.urlToImage,
                ),
                contentScale = ContentScale.FillBounds,
                contentDescription = stringResource(R.string.desc_article_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(newsImageHeight)
                    .padding(bottom = newsImageBottomPadding)
            )

            Text(
                text = article?.title ?: "",
                style = MaterialTheme.typography.h6,
                color = Color.Blue,
                modifier = Modifier.padding(horizontal = newsTextHorizontalPadding)
            )
            Text(
                text = article?.source ?: "",
                color = Color.DarkGray,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(horizontal = newsTextHorizontalPadding)
            )
            Text(
                text = article?.publishedAt ?: "",
                color = Color.DarkGray,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(horizontal = newsTextHorizontalPadding)
            )

        }
    }
}