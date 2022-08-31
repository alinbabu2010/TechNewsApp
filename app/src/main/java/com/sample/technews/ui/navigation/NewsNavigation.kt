@file:OptIn(ExperimentalAnimationApi::class)

package com.sample.technews.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sample.technews.ui.screens.details.DetailScreen
import com.sample.technews.ui.screens.list.ListScreen

@Composable
fun NewsNavigation() {

    val navController = rememberAnimatedNavController()
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
            ListScreen(navController)
        }

        composable(NewsScreens.DETAIL_SCREEN.name) {
            DetailScreen()
        }


    }

}

enum class NewsScreens {
    LIST_SCREEN,
    DETAIL_SCREEN
}