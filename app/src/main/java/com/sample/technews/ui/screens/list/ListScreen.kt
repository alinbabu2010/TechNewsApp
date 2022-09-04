package com.sample.technews.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sample.technews.R
import com.sample.technews.domain.model.ArticleInfo
import com.sample.technews.ui.utils.*

@Composable
fun ListScreen(navController: NavHostController? = null) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        val listViewModel: ListViewModel = hiltViewModel()
        val lazyNewsItems: LazyPagingItems<ArticleInfo> =
            listViewModel.getArticles().collectAsLazyPagingItems()

        val swipeRefreshState = rememberSwipeRefreshState(false)

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                lazyNewsItems.refresh()
                swipeRefreshState.isRefreshing = true
            }
        ) {

            LazyColumn {
                items(lazyNewsItems) { article ->
                    NewsItem(article = article)
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
                            swipeRefreshState.isRefreshing = false
                        }
                    }
                }

            }

        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsItem(article: ArticleInfo?) {

    Card(
        shape = RoundedCornerShape(newsItemCardCornerSize),
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(
                horizontal = newsImageCardHorizontalPadding,
                vertical = newsImageCardVerticalPadding
            )
            .fillMaxWidth(),
        backgroundColor = Color.LightGray
    )

    {
        Column(Modifier.padding(newsImageCardVerticalPadding)) {

            Image(
                painter = rememberAsyncImagePainter(
                    model = article?.urlToImage,
                ),
                contentScale = ContentScale.FillBounds,
                contentDescription = stringResource(R.string.desc_article_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(newsImageHeight)
            )

            Text(text = article?.title ?: "", style = MaterialTheme.typography.h6)
            Text(text = article?.author ?: "", style = MaterialTheme.typography.subtitle1)
            Text(text = article?.publishedAt ?: "", style = MaterialTheme.typography.subtitle2)

        }
    }
}