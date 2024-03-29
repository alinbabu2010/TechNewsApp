@file:OptIn(ExperimentalAnimationApi::class)

package com.sample.technews.ui.navigation

import android.net.Uri
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.sample.technews.domain.model.ArticleInfo
import com.sample.technews.ui.screens.details.DetailScreen
import com.sample.technews.ui.screens.list.ListScreen
import com.sample.technews.ui.screens.list.ListViewModel

const val NAV_ARG = "ARTICLE_INFO"

enum class NewsScreens {
    LIST_SCREEN,
    DETAIL_SCREEN
}

@Composable
fun NewsNavigation() {

    val navController = rememberAnimatedNavController()
    val springSpec = spring<IntOffset>(dampingRatio = 2F)

    val listViewModel: ListViewModel = hiltViewModel()
    val lazyNewsItems = listViewModel.getArticles().collectAsLazyPagingItems()

    AnimatedNavHost(
        navController = navController,
        startDestination = NewsScreens.LIST_SCREEN.name,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = springSpec)
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = springSpec)
        }) {


        composable(NewsScreens.LIST_SCREEN.name) {
            ListScreen(lazyNewsItems) {
                val article = Uri.encode(Gson().toJson(it))
                val route = "${NewsScreens.DETAIL_SCREEN.name}/$article"
                navController.navigate(route)
            }
        }

        val route = "${NewsScreens.DETAIL_SCREEN.name}/{${NAV_ARG}}"
        composable(route = route, arguments = listOf(
            navArgument(NAV_ARG) {
                this.type = ArticleInfoType()
            }
        )) { navBackStackEntry ->
            val articleInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                navBackStackEntry.arguments?.getParcelable(NAV_ARG, ArticleInfo::class.java)
            } else {
                @Suppress("DEPRECATION")
                navBackStackEntry.arguments?.getParcelable(NAV_ARG)
            }
            articleInfo?.let { DetailScreen(it) }
        }

    }

}