@file:OptIn(ExperimentalAnimationApi::class)

package com.sample.technews.ui.navigation

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.sample.technews.domain.model.ArticleInfo
import com.sample.technews.ui.screens.details.DetailScreen
import com.sample.technews.ui.screens.list.ListScreen

const val NAV_ARG = "ARTICLE_INFO"

enum class NewsScreens {
    LIST_SCREEN,
    DETAIL_SCREEN
}

private lateinit var navController : NavHostController

@Composable
fun NewsNavigation() {

    navController = rememberAnimatedNavController()
    val springSpec = spring<IntOffset>(dampingRatio = 2F)

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
            ListScreen()
        }

        val route = "${NewsScreens.DETAIL_SCREEN.name}/{${NAV_ARG}}"
        composable(route = route, arguments = listOf(
            navArgument(NAV_ARG) {
                this.type = ArticleInfoType()
            }
        )) { navBackStackEntry ->
            val articleInfo = navBackStackEntry.arguments?.getParcelable<ArticleInfo>(NAV_ARG)
            articleInfo?.let { DetailScreen(it) }
        }

    }

}

fun navigateToDetails(articleInfo: ArticleInfo) {
    val article = Uri.encode(Gson().toJson(articleInfo))
    val route = "${NewsScreens.DETAIL_SCREEN.name}/$article"
    navController.navigate(route)
}